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
import javax.transaction.Transactional;
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
    private String name1 = "Olavi Uusivirta";
    private String username1 = "olleee";
    private String password1 = "raivo_harka";
    private String profile1 = "olavi-uusivirta";
    private String name2 = "Touko Aalto";
    private String username2 = "toukonen";
    private String password2 = "vihreat";
    private String profile2 = "touko-aalto";
    
    @Autowired
    SignupService signupService;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    RequestRepository requestRepository;
    
    @Autowired
    SkillService skillService;
    
    @Autowired
    ContactService contactService;
    
    private void initRepository() {
        accountRepository.deleteAll();
    }
    
    private void initUser2() {
        String name = name2;
        String username = username2;
        String password = password2;
        String profile = profile2;
        byte[] image = new byte[20];
        new Random().nextBytes(image);
        
        SignupForm signup = new SignupForm(name, username, password, profile);
        signupService.createNewAccount(signup);
    }
    
    private void initUser1() {
        String name = name1;
        String username = username1;
        String password = password1;
        String profile = profile1;
        byte[] image = new byte[20];
        new Random().nextBytes(image);
        
        SignupForm signup = new SignupForm(name, username, password, profile);
        signupService.createNewAccount(signup);
    }
    
    @Test
    public void testRepoEmpty() {
        initRepository();
        assertEquals(0, accountRepository.count());
    }
    
    @Test
    public void testFindProfile() {
        
        testRepoEmpty();
        assertNull(accountRepository.findByUsername(username1));
        assertNull(accountRepository.findByProfile(username1));
        
        initUser1();
        assertEquals(1, accountRepository.count());
        Account account = accountRepository.findByUsername(username1);
        Account same_account = accountRepository.findByProfile(profile1);
        assertEquals(name1, account.getName());
        assertNull(account.getPicture());
        assertNull(same_account);
    }
    
    @Test
    public void testProfilePicInit() {
        testRepoEmpty();
        initUser1();
        
        assertEquals(1, accountRepository.count());
        Account account = accountRepository.findByUsername(username1);
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
    
    
    @Test
    @Transactional
    public void addRequests() {
        testRepoEmpty();
        initUser1();
        initUser2();
        Account source = accountRepository.findByName(name1);
        Account target = accountRepository.findByName(name2);
        
        assertEquals(0, requestRepository.count());
        
        testRequestConditionsPass(source, target);
        
        contactService.addContactRequest(source, target.getProfile());
        assertEquals(1, requestRepository.count());
        
        
    }
    
    public void testRequestConditionsPass(Account source, Account target) {
    
        boolean submitterHasRequest = contactService.getPendingRequestsFromOne(source, target).size() == 1 ? true : false;
        boolean areFriends = contactService.findIfOneWayContacts(source, target);
        boolean areSame = source.getProfile().equals(target.getProfile());
        boolean blocked = contactService.isRequesterBlocked(source, target);
        
        assertFalse(submitterHasRequest);
        assertFalse(areFriends);
        assertFalse(areSame);
        assertFalse(blocked);
    }
    
    public void testifBlocked() {
        testRepoEmpty();
        initUser1();
        initUser2();
        Account source = accountRepository.findByName(name1);
        Account target = accountRepository.findByName(name2);
        
        assertEquals(0, requestRepository.count());
        Request latest = contactService.findLatestRequestByTargetAndSubmitter(target.getPassword(), source);
        assertNull(latest);
    }
    
}
