package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    
    @GetMapping("/me")
    public String routeProfile() {
        String profile_id;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        profile_id = "/profile/" + account.getProfile();
        return "redirect:"+profile_id;
    }
    
    @GetMapping("/profile/{profile}")
    public String getProfile(@PathVariable String profile, Model model) {
        Account account = accountRepository.findByProfile(profile);
        if (account == null) {
            return "redirect:/";
            /*throw new Exception("No user with this id.");*/
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
        
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/";
        }
        
        byte [] picture = new byte[0];
        Account account = new Account(name, username, passwordEncoder.encode(password), profile, picture);
        accountRepository.save(account);
        
        return "redirect:/";
    }
}
