/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

/**
 *
 * @author piaandersin
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class SignupService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    public String getProfilePath() {
        String profile_path;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        profile_path = "/profile/" + account.getProfile();
        return profile_path;
    }
    
    public Account getAccountByProfile (String profile) {
        Account account = accountRepository.findByProfile(profile);
        return account;
    }
    
    public Account getAccountByUsername (String username) {
        Account account = accountRepository.findByUsername(username);
        return account;
    }
    
    @Transactional
    public void createNewAccount(SignupForm signup) {
        
        Account new_account = new Account();
        new_account.setName(signup.getName());
        new_account.setUsername(signup.getUsername());
        new_account.setProfile(signup.getProfile());
        new_account.setPassword(passwordEncoder.encode(signup.getPassword()));

        accountRepository.save(new_account);
       
    }
}
/*
String name, String username, String password, String profile
Account new_account = new Account(name, username, passwordEncoder.encode(password), profile, picture);
*/