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

import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.CascadeType;
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
    
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Skill> skills = new ArrayList<>();
    
    //@Lob annotation is not supported by Heroku Postgres
    @Column(length = 16000000)
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
}
