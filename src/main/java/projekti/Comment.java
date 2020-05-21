/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.sql.Timestamp;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.ManyToOne;

/**
 *
 * @author piaandersin
 */

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Comment extends AbstractPersistable<Long> {
    
    private String content;
    private Timestamp created;
    private String commentorName;
    private String commentorProfile;
}
