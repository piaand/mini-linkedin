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
import java.sql.Timestamp;
import java.util.Date;

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
    
    public Timestamp getDateTimeNow() {
        Date date= new Date();
         //getTime() returns current time in milliseconds
	long time = date.getTime();
        Timestamp created = new Timestamp(time);
        return created;
    }
    
    @Transactional
    public void addContactRequest(Account requester, String target) {
        
        Timestamp created = getDateTimeNow();
        Request request = new Request(requester, target, "pending", created);
        requestRepository.save(request);
    }
    
    @Transactional
    public void setRequestToStatus(Request request, String status){
        if (requestRepository.existsById(request.getId())) {
            request.setStatus(status);
            request.setModified(getDateTimeNow());
            requestRepository.save(request);
        }
    }
    
    @Transactional
    private boolean deleteContact(Account source, Account target) {
        boolean deleted;
        if (source.getContacts().contains(target)) {
            source.getContacts().remove(target);
            accountRepository.save(source);
            deleted = true;
        }
        
        if (target.getContacts().contains(source)) {
            target.getContacts().remove(source);
            accountRepository.save(target);
            deleted = true;
        }
        return true;
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
    
    public List<Request> getAccountsPendingRequest(Account account) {
        String target = account.getProfile();
        List<Request> invitations = requestRepository.findByTargetAndStatus(target, "pending");
        return invitations;
    }
    
    public boolean findIfHasPendingRequests (Account account) {
        List<Request> invitations = getAccountsPendingRequest(account);
        return !(invitations.isEmpty());
    }
    
    public List <Request> getPendingRequestsFromOne (Account source, Account target) {
        List <Request> requests = requestRepository.findByTargetAndSubmitterAndStatus(source.getProfile(), target, "pending");
        return requests;
    }
    
    public Request findLatestRequestByTargetAndSubmitter(String target, Account submitter) {
        List <Request> requests = requestRepository.findByTargetAndSubmitter(target, submitter);
        if (requests.isEmpty()) {
            return null;
        }
        Request latest = requests.get(0);
        for (Request r : requests) {
            if (r.getModified().after(latest.getModified())) {
                latest = r;
            }
        }
        return latest;
    }
    
    public Request findLatestRequest(Account one, Account two) {
        Request two_latest = findLatestRequestByTargetAndSubmitter(one.getProfile(), two);
        Request one_latest = findLatestRequestByTargetAndSubmitter(two.getProfile(), one);
        Request latest;
        
        if (one_latest != null && two_latest != null) {
            latest = one_latest.getModified().after(two_latest.getModified()) ? one_latest : two_latest;
        } else {
            latest = one_latest != null ? one_latest : two_latest;
        }
        
        return latest;
    }
    
    public boolean isRequesterBlocked(Account requester, Account target) {
        Request latest = findLatestRequest(requester, target);
        
        if (latest != null) {
            if (latest.getSubmitter().equals(requester) && (latest.getStatus().equals("pending") || latest.getStatus().equals("rejected"))) {
                return true;
            } else if (latest.getSubmitter().equals(target) && latest.getStatus().equals("deleted")) {
                return true; 
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    @PreAuthorize("#requester.username == authentication.principal.username")
    public boolean doContactRequest(Account requester, String target_profile) {
        Account target = accountRepository.findByProfile(target_profile);
           
        boolean submitterHasRequest = getPendingRequestsFromOne(requester, target).size() == 1 ? true : false;
        boolean areFriends = findIfOneWayContacts(requester, target);
        boolean areSame = requester.getProfile().equals(target_profile);
        boolean blocked = isRequesterBlocked(requester, target);
        
        if (areFriends || areSame || blocked || submitterHasRequest) {
            return false;
        } else {
            addContactRequest(requester, target_profile);
            return true; 
        }
    }
    
    @PreAuthorize("#accepter.username == authentication.principal.username")
    public boolean addToNetwork(Account requester, Account accepter) {
        if (requester != null && accepter != null) {
            boolean added = createContact(requester, accepter);
            List <Request> requests = requestRepository.findByTargetAndSubmitterAndStatus(accepter.getProfile(), requester, "pending");
            if (requests.size() == 1) {
                setRequestToStatus(requests.get(0), "accepted");
            } else {
                added = false;
            }
            return added;
        } else {
            return false;
        }
    }
    
    public void acceptedRequestToDeleted(Account source, Account target) {
      Request latest = findLatestRequest(source, target);
      if (latest != null && latest.getStatus().equals("accepted")) {
          setRequestToStatus(latest, "deleted");
      }
    }
    
    public boolean rejectRequest(Account source, Account target) {
         List <Request> requests = requestRepository.findByTargetAndSubmitterAndStatus(source.getProfile(), target, "pending");
        if (requests.size() == 1) {
            setRequestToStatus(requests.get(0), "rejected");
            return true;
        } else {
            return false;
        }
    }
    
    @PreAuthorize("#source.username == authentication.principal.username")
    public boolean deleteFromNetwork(Account source, Account target) {
        if (source != null && target != null) {
            boolean removed;
            if (target.getContacts().contains(source)) {
                removed = deleteContact(source, target);
                acceptedRequestToDeleted(source, target);
            } else {
               removed = rejectRequest(source, target);
            }
            return removed;
        } else {
            return false;
        }
    }
    
}
