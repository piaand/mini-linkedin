/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 *
 * @author piaandersin
 */
public interface SkillVoteRepository extends JpaRepository<SkillVote, Long> {
    List <SkillVote> findByTalent(Account talent, Pageable pageable);
    SkillVote findByTalentAndSkillId(Account talent, Long skillId);
}
