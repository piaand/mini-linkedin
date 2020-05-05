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

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
 
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Account extends AbstractPersistable<Long> {
    
    private String name;
    private String username;
    private String password;
    
    @Column(name="profile", unique=true)
    private String profile;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
}
