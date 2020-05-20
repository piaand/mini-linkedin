/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.Base64;
import java.util.List;

/**
 *
 * @author piaandersin
 */
@Controller
public class SignupController {
    
    @Autowired
    private SignupService signupService;
    
    @Autowired
    private SkillService skillService;
    
    @Autowired
    private ContactService contactService;
    
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
    
    @GetMapping("/me")
    public String routeProfile() {
        String profile_path;
        
        profile_path = signupService.getProfilePath();
        return "redirect:"+profile_path;
    }
    
    @GetMapping("/me/settings")
    public String getModifyProfile() {
        String profile = signupService.getAuthProfileString();
        
        String profile_path = "/profile/" + profile + "/settings";
        return  "redirect:"+profile_path;
    }
    
    @GetMapping("/profile/{profile}")
    public String getProfile(@PathVariable String profile, Model model) {
        Account account = signupService.getAccountByProfile(profile);
        if (account == null) {
            return "redirect:/";
            //future throw exception
        }
        
        byte[] imageInByte = account.getPicture();
        String image_string = Base64.encodeBase64String(imageInByte);
        List <Request> invitations = contactService.getAccountsPendingRequest(account);

        model.addAttribute("account", account);
        model.addAttribute("image", image_string);
        model.addAttribute("requests", invitations);
        model.addAttribute("contacts", account.getContacts());
        model.addAttribute("skills", skillService.getAllSortedUserSkills(account));
        return "profile";
    }
    
    @GetMapping("/signup")
    public String getSignup() {
        
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signupUser(@Valid @ModelAttribute SignupForm signup, BindingResult bindingResult) {
        String old_name;
        if (bindingResult.hasErrors()) {
            return "redirect:/signup";
        }
        
        old_name = signup.getUsername();
        Account old_account = signupService.getAccountByUsername(old_name);
        if (old_account != null) {
            return "redirect:/signup";
        }
        
        signupService.createNewAccount(signup);
        return "redirect:/";
    }
    
    @PostMapping("/search")
    public String searchProfile(@RequestParam String name) {
        Account account = signupService.getAccountByName(name);
        if (account == null) {
            return "redirect:/";
        } else {
           String profile = account.getProfile();
           String path = "/profile/" + profile;
           return "redirect:" + path; 
        }
    }
}
