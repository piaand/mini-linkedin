package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String helloWorld(Model model) {
        //model.addAttribute("message", "World!");
        return "index";
    }
    
    @GetMapping("/profile")
    public String getProfile() {
        
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
        
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/";
        }
        
        byte [] picture = new byte[0];
        Account account = new Account(name, username, passwordEncoder.encode(password), profile, picture);
        accountRepository.save(account);
        
        return "redirect:/";
    }
}
