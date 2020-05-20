/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author piaandersin
 */

@Controller
public class ContactController {
    
    @Autowired
    private SignupService signupService;
    
    @Autowired
    private ContactService contactService;
    
    @PostMapping("/contact/{sender}/{profile_target}")
    public String sendContactRequest(@PathVariable String sender, @PathVariable String profile_target) {
        Account requester = signupService.getAccountByUsername(sender);
        
        boolean added = contactService.doContactRequest(requester, profile_target);
        String path = "/profile/" + requester.getProfile();
        return "redirect:" + path;
    }
    
    @PostMapping("/contact/{sender}/{profile_target}/add")
    public String addContact(@PathVariable String sender, @PathVariable String profile_target) {
        Account requester = signupService.getAccountByUsername(sender);
        Account accepter = signupService.getAccountByProfile(profile_target);
        
        boolean added = contactService.addToNetwork(requester, accepter);
        String path = "/profile/" + profile_target;
        return "redirect:" + path;
    }
    
    @PostMapping("/contact/{sender}/{profile_target}/delete")
    public String denyRequest(@PathVariable String sender, @PathVariable String profile_target) {
        //if there is contact, deletes it. Changes status to "deleted" or "rejected"
        Account requester = signupService.getAccountByUsername(sender);
        Account accepter = signupService.getAccountByProfile(profile_target);
        
        boolean added = contactService.deleteFromNetwork(requester, accepter);
        String path = "/profile/" + requester.getProfile();
        return "redirect:" + path;
    }
}
