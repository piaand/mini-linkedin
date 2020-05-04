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
    public void createNewAccount(String name, String username, String password, String profile) {
        
        byte [] picture = new byte[0];
        Account new_account = new Account(name, username, passwordEncoder.encode(password), profile, picture);
        accountRepository.save(new_account);
    }
}
