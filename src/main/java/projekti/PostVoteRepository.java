package projekti;


import org.springframework.data.jpa.repository.JpaRepository;
import projekti.Vote;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author piaandersin
 */

public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    
}
