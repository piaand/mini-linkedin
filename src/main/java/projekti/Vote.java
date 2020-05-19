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
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author piaandersin
 */

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Vote extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account voter;
    private String targetProfile;
    private Long targetSkill;
    
}
