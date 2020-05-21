/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

/**
 *
 * @author piaandersin
 */

@Controller
public class BoardController {
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    SignupService signupService;
    
    @GetMapping("/board")
    public String getBoard(Model model) {
        Account logged = signupService.getAuthAccount();
        
        model.addAttribute("messages", messageService.getViewableMessages(logged));
        return "board";
    }
    
    @PostMapping("/board")
    public String addToBoard(@Valid @ModelAttribute TextContent content, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        Account author = signupService.getAuthAccount();
        messageService.addMessage(author, content.getContent());
        return "redirect:/board";
    }
    
    @PostMapping("/board/{message_id}")
    public String addToMessage(@Valid @ModelAttribute TextContent content, BindingResult bindingResult, @PathVariable Long message_id) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        Account author = signupService.getAuthAccount();
        Message message = messageService.getMessagaByID(message_id);
        messageService.addComment(author, content.getContent(), message);
        return "redirect:/board";
    }
    
     @PostMapping("/board/{message_id}/like")
    public String likeToMessage(@PathVariable Long message_id) {
        
        Account author = signupService.getAuthAccount();
        Message message = messageService.getMessagaByID(message_id);
        messageService.likeMessage(author, message);
        return "redirect:/board";
    }
    
}
