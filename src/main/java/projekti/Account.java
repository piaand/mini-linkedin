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
import javax.persistence.OneToMany;
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
   
    @OneToMany(mappedBy = "submitter")
    private List<Request> requests = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Account> contacts = new ArrayList<>();
    
    @OneToMany(mappedBy = "voter")
    private List<Vote> givenVotes = new ArrayList<>();
    
    @OneToMany(mappedBy = "liker")
    private List<PostVote> givenLikes = new ArrayList<>();
    
    @OneToMany(mappedBy = "talent")
    private List<SkillVote> voted_skills = new ArrayList<>();
    
    //@Lob annotation is not supported by Heroku Postgres
    @Column(length = 16000000)
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
}
