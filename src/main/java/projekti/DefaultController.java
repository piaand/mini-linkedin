package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;
    
    @GetMapping("/")
    public String helloWorld(Model model) {
        //model.addAttribute("message", "World!");
        return "index";
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
        
       /* if (accountRepository.findByUserName(username) != null) {
            return "redirect:/";
        }*/
        
        byte [] picture = new byte[0];
        Account account = new Account(name, username, password, profile, picture);
        accountRepository.save(account);
        
        return "redirect:/";
    }
}
