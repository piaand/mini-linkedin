/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;


/**
 *
 * @author piaandersin
 */

@Controller
public class ProfileController {
    
    @Autowired
    private SignupService signupService;
    
    @Autowired
    private SkillService skillService;
    
    
    @GetMapping("/profile/{profile}/settings")
    public String getSettingsPage(@PathVariable String profile, Model model) {
        Account account = signupService.getAccountByProfile(profile);
        String image_string = signupService.getSettingPicture(account);
        
        model.addAttribute("account", account);
        model.addAttribute("image", image_string);
        model.addAttribute("skills", skillService.getAllUserSkills(account));
        return "settings";
    }
    
    
    @PostMapping("/profile/{profile}/profile-picture")
    public String savePicture(@RequestParam("picture") MultipartFile file, @PathVariable String profile) throws IOException {
        try {
            if (!file.getContentType().equals("image/jpeg")) {
                return "redirect:/me/settings";
            }
            
            Account account = signupService.getAccountByProfile(profile);
            signupService.saveProfilePicture(file.getBytes(), account);
            return "redirect:/me/settings";
        } catch (IOException e) {
            return "redirect:/me/settings";
        }
        
    }
    
    @PostMapping("/profile/{profile}/profile-picture/delete")
    public String deletePicture(@PathVariable String profile) {
            Account account = signupService.getAccountByProfile(profile);

            signupService.deleteProfilePicture(account);
            return "redirect:/me/settings";
    }
    
    @PostMapping("/profile/{profile}/skills")
    public String addSkill(@PathVariable String profile, @RequestParam String skill) {
            Account account = signupService.getAccountByProfile(profile);
            
            skillService.addNewSkill(account, skill);
            return "redirect:/me/settings";
    }
    
    @PostMapping("/profile/{profile}/skills/{id}/delete")
    public String deleteSkillFromProfile(@PathVariable String profile, @PathVariable Long id) {
            Account account = signupService.getAccountByProfile(profile);
            
            skillService.deleteProfileSkill(account, id);
            return "redirect:/me/settings";
    }
    
    @PostMapping("/profile/{profile}/skills/{id}/up/{voter}")
    public String voteUpSkill(@PathVariable String profile, @PathVariable Long id, @PathVariable String voter) {
        Account account = signupService.getAccountByUsername(voter);
        
        skillService.upvoteSkill(account, profile, id);
        String path = "/profile/" + profile;
        return "redirect:" + path;
    }
}
