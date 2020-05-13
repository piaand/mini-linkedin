/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
 * @author piaandersin
 */

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    
    public List<Request> getAccountsPendingRequest(Account account) {
        String target = account.getProfile();
        List<Request> invitations = requestRepository.findByTargetAndStatus(target, "pending");
        return invitations;
    }
}
