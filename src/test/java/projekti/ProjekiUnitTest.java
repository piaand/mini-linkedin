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
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
 
 
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjekiUnitTest {
    @Autowired
    SignupService signupService;
    
    @Autowired
    AccountRepository accountRepository;
    
    private void initRepository() {
        accountRepository.deleteAll();
    }
    
    @Test
    public void testRepoEmpty() {
        initRepository();
        assertEquals(0, accountRepository.count());
    }
    
    @Test
    public void testFindProfile() {
        String name = "Olavi Uusivirta";
        String username = "olleee";
        String password = "raivo_harka";
        String profile = "olavi-uusivirta";
        byte[] image = new byte[20];
        new Random().nextBytes(image);
        
        testRepoEmpty();
        
        SignupForm signup = new SignupForm(name, username, password, profile);
        assertNull(accountRepository.findByUsername(username));
        assertNull(accountRepository.findByProfile(username));
        signupService.createNewAccount(signup);
        assertEquals(1, accountRepository.count());
        Account account = accountRepository.findByUsername(username);
        Account same_account = accountRepository.findByProfile(profile);
        assertEquals(name, account.getName());
        assertNull(account.getPicture());
        assertNull(same_account);
    }
    
    @Test
    public void testProfilePicInit() {
        String name = "Olavi Uusivirta";
        String username = "olleee";
        String password = "raivo_harka";
        String profile = "olavi-uusivirta";
        byte[] image = new byte[20];
        new Random().nextBytes(image);
        
        testRepoEmpty();
        
        SignupForm signup = new SignupForm(name, username, password, profile);
        signupService.createNewAccount(signup);
        assertEquals(1, accountRepository.count());
        Account account = accountRepository.findByUsername(username);
        assertNull(account.getPicture());
    }
    
    @Test
    public void testDifferentProfileStrings() {
    String name = "Olavi Uusivirta";
        String username = "olleee";
        String password = "raivo_harka";
        String profile = "olavi-uusivirta";
        Integer i;
        boolean same = true;
        byte[] image = new byte[20];
        new Random().nextBytes(image);
        
        testRepoEmpty();
        
        SignupForm signup = new SignupForm(name, username, password, profile);
        SignupForm same_signup = new SignupForm(name, username, password, profile);
        
        signupService.createNewAccount(signup);
        assertEquals(1, accountRepository.count());
        signupService.createNewAccount(same_signup);
        assertEquals(2, accountRepository.count());
        List olavi = accountRepository.findAll();
        List profiles = new ArrayList<String>();
        for (i = 0; i < olavi.size(); i++) {
           Account artist = (Account)olavi.get(i);
           profiles.add(artist.getProfile());
        }
        assertEquals(profiles.size(), olavi.size());
        String placeholder = (String) profiles.get(0);
        
        for (i = 0; i < profiles.size(); i++) {
           String chars = (String) profiles.get(i);
           if (!(placeholder.equals(chars))) {
                same = false;
           }
        }
        assertFalse(same);
    }
}
