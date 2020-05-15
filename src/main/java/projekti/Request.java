/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.sql.Timestamp;

/**
 *
 * @author piaandersin
 */

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Request extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account submitter;
    
    private String target;
    private String status;
    private Timestamp modified;
}
