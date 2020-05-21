/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author piaandersin
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List <Message> findByAuthorProfileIn(List<String> profiles, Pageable pageable);
}
