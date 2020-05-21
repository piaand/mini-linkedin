/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.OneToMany;

/**
 *
 * @author piaandersin
 */

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Message extends AbstractPersistable<Long> {
    private String authorProfile;
    private String authorName;
    private String content;
    private Timestamp created;
    
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();
}
