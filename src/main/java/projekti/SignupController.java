/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("name", account.getName());
        return "profile";
    }
    
    @GetMapping("/signup")
    public String getSignup() {
        
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signupUser(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String username,
            @RequestParam String profile) {
        
        Account account = signupService.getAccountByUsername(username);
        if (account != null) {
            return "redirect:/";
        }
        
        signupService.createNewAccount(name, username, password, profile);
        return "redirect:/";
    }
}
