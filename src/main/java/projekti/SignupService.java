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
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
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
    
    private Account getAuthAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        return account;
    }
    
    public String getProfilePath() {
        String profile_path;
        
        Account account = getAuthAccount();
        profile_path = "/profile/" + account.getProfile();
        return profile_path;
    }
    
    public Account getAccountById (Long id) {
        Account account = accountRepository.getOne(id);
        return account;
    }
    
    public Account getAccountByProfile (String profile) {
        Account account = accountRepository.findByProfile(profile);
        return account;
    }
    
    public Account getAccountByUsername (String username) {
        Account account = accountRepository.findByUsername(username);
        return account;
    }
    
    public void initSkills(Account account) {
        List <Skill> skill_list = new ArrayList<>();
        account.setSkills(skill_list);
    }
    
    @Transactional
    public void createNewAccount(SignupForm signup) {
        
        Account new_account = new Account();
        new_account.setName(signup.getName());
        new_account.setUsername(signup.getUsername());
        
        String profile = signup.getProfile() + "-";
        String id = UUID.randomUUID().toString().substring(0,6);
        new_account.setProfile(profile + id);
        initSkills(new_account);
        
        new_account.setPassword(passwordEncoder.encode(signup.getPassword()));

        accountRepository.save(new_account);
       
    }
    
    @Transactional
    public void saveProfilePicture(byte[] picture, Account account) {
        account.setPicture(picture);
    }
    
    @Transactional
    public void deleteProfilePicture(Account account) {
        account.setPicture(null);
    }
    
}
