/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author piaandersin
 */

@Controller
public class ProfileController {
    
    @Autowired
    private SignupService signupService;
    
    @PostMapping("/profile-picture")
    public String savePicture(@RequestParam("picture") MultipartFile file) throws IOException {
        try {
            if (!file.getContentType().equals("image/jpeg")) {
                return "redirect:/me";
            }

            signupService.saveProfilePicture(file.getBytes());
            return "redirect:/me";
        } catch (IOException e) {
            return "redirect:/me";
        }
        
    }
    
    @PostMapping("/profile-picture/{profile}/delete")
    public String deletePicture(@PathVariable String profile) {
            Account account = signupService.getAccountByProfile(profile);

            signupService.deleteProfilePicture(account);
            return "redirect:/me";
    }
}