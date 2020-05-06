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
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);
}
