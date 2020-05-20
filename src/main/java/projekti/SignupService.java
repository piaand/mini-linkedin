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
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.access.prepost.PreAuthorize;

@Service
public class SignupService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RequestRepository requestRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Account getAuthAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        return account;
    }
    
    public String getAuthProfileString() {
        String profile_string;
        
        Account account = getAuthAccount();
        profile_string = account.getProfile();
        return profile_string;
    }
    
    public String getProfilePath() {
        String profile_path;
        
        profile_path = "/profile/" + getAuthProfileString();
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
    
    public Account getAccountByName (String name) {
        Account account = accountRepository.findByName(name);
            return account;
    }
    
    @PreAuthorize("#account.username == authentication.principal.username")
    public String getSettingPicture(Account account) {
        byte[] imageInByte = account.getPicture();
        String image_string = Base64.encodeBase64String(imageInByte);
        return image_string;
    }
    
    @Transactional
    public void createNewAccount(SignupForm signup) {
        
        Account new_account = new Account();
        new_account.setName(signup.getName());
        new_account.setUsername(signup.getUsername());
        
        String profile = signup.getProfile() + "-";
        String id = UUID.randomUUID().toString().substring(0,6);
        new_account.setProfile(profile + id);
        
        new_account.setPassword(passwordEncoder.encode(signup.getPassword()));

        accountRepository.save(new_account);
       
    }
    
    @PreAuthorize("#account.username == authentication.principal.username")
    @Transactional
    public void saveProfilePicture(byte[] picture, Account account) {
        account.setPicture(picture);
    }
    
    @PreAuthorize("#account.username == authentication.principal.username")
    @Transactional
    public void deleteProfilePicture(Account account) {
        account.setPicture(null);
    }
    
    
    
}
