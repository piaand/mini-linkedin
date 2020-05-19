/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 *
 * @author piaandersin
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByTargetSkillAndTargetProfileAndVoter(Long tragetSkill, String targetProfile, Account voter);
}
