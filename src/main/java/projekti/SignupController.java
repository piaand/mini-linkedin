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

/**
 *
 * @author piaandersin
 */
@Controller
public class SignupController {
    
    @Autowired
    private SignupService signupService;
    
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
    
    @GetMapping("/profile/{profile}")
    public String getProfile(@PathVariable String profile, Model model) {
        Account account = signupService.getAccountByProfile(profile);
        if (account == null) {
            return "redirect:/";
            //future throw exception
        }
        
        byte[] imageInByte = account.getPicture();
        String image_string = Base64.encodeBase64String(imageInByte);
        model.addAttribute("account", account);
        model.addAttribute("image", image_string);
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
