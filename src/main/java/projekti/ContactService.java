/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
/**
 *
 * @author piaandersin
 */

@Service
public class ContactService {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RequestRepository requestRepository;
    
    @Transactional
    private void addContactRequest(Account requester, String target) {
        
        requestRepository.save(new Request(requester, target, "pending"));
    }
    
    @Transactional
    public void setRequestAccepted(Request request){
        if (requestRepository.existsById(request.getId())) {
            request.setStatus("accepted");
            requestRepository.save(request);
        }
    }
    
    @Transactional
    public void setRequestDeleted(Request request){
        // tag will be given when contact is deleted
        if (requestRepository.existsById(request.getId())) {
            request.setStatus("deleted");
            requestRepository.save(request);
        }
    }
    
    @Transactional
    private boolean createContact(Account requester, Account accepter) {
        boolean connection = false;
        if (!requester.getContacts().contains(accepter)) {
            requester.getContacts().add(accepter);
            accountRepository.save(requester);
            connection = true;
        }
        
        if (!accepter.getContacts().contains(requester)) {
            accepter.getContacts().add(requester);
            accountRepository.save(accepter);
            connection = true;
        }
        return connection;
    }
    
    public boolean findIfOneWayContacts(Account source, Account target) {
        if (source.getContacts().contains(target)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    @PreAuthorize("#requester.username == authentication.principal.username")
    public boolean doContactRequest(Account requester, String target) {
        List <String> targetProfiles = requestRepository.findTarget(requester);
        Account account = accountRepository.findByProfile(target);
        List <String> arrivingRequests = requestRepository.findTarget(account);
        
        boolean submitterHasRequest;
        
        if (arrivingRequests.contains(requester.getProfile())) {
            Request request = requestRepository.findByTargetAndSubmitter(requester.getProfile(), account);
            String status = request.getStatus();
            if (status.equals("pending")) {
                submitterHasRequest = true;
            } else {
                submitterHasRequest = false;
            }
        } else {
            submitterHasRequest = false;
        }
        
        boolean areFriends = findIfOneWayContacts(requester, account);
        boolean areSame = requester.getProfile().equals(target);
        boolean requestHasBeenMade = targetProfiles.contains(target);
        
        if (areFriends || areSame || requestHasBeenMade || submitterHasRequest) {
            return false;
        } else {
            addContactRequest(requester, target);
            return true; 
        }
    }
    
    @PreAuthorize("#accepter.username == authentication.principal.username")
    public boolean addToNetwork(Account requester, Account accepter) {
        if (requester != null && accepter != null) {
            boolean added = createContact(requester, accepter);
            Request request = requestRepository.findByTargetAndSubmitter(accepter.getProfile(), requester);
            setRequestAccepted(request);
            return added;
        } else {
            return false;
        }
    }
    
    public List<Request> getAccountsPendingRequest(Account account) {
        String target = account.getProfile();
        List<Request> invitations = requestRepository.findByTargetAndStatus(target, "pending");
        return invitations;
    }
}
