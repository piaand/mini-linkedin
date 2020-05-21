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
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    /*
    @Autowired
    CommentRepository commentRepository;
    */
    @Autowired
    ContactService contactService;
    
    public Message createNewMessage(Account author, String content) {
        Timestamp created = contactService.getDateTimeNow();
        Message message = new Message();
        message.setAuthorName(author.getName());
        message.setAuthorProfile(author.getProfile());
        message.setContent(content);
        message.setCreated(created);
        return message;
    }
    
    public Pageable createPageableSortDate(int amount){
        Pageable pageable = PageRequest.of(0, amount, Sort.by("created").descending());
        return pageable;
    }
    
    public List <String> getContactProfiles(Account account) {
        List <Account> friends = account.getContacts();
        List <String> profiles = new ArrayList<>();
        for (Account friend : friends) {
            profiles.add(friend.getProfile());
        }
        return profiles;
    }
    
    @PreAuthorize("#author.username == authentication.principal.username")
    @Transactional
    public void addMessage(Account author, String content) {
       Message message = createNewMessage(author, content);
       messageRepository.save(message);
    }
    
    
    public List<Message> getViewableMessages(Account logged) {
        Pageable page = createPageableSortDate(25);
        List <String> profiles = getContactProfiles(logged);
        profiles.add(logged.getProfile());
        
        List<Message> messages = messageRepository.findByAuthorProfileIn(profiles, page);
        if (messages == null) {
            List<Message> new_messages = new ArrayList<>();
            return new_messages;
        }
        return messages;
    }
}
