/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 *
 * @author piaandersin
 */

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    private String trim_skill(String skill) {
        String trimmed;
        
        if ((skill == null) || (skill.isBlank()))  {
            return null;
        } else {
            trimmed = skill.trim().toLowerCase();
            return trimmed;
        }
    }
    
    private boolean accountHasSkill(Account account, String skill) {
        
        boolean exists = false;
        
        List <Skill> skills = getAllUserSkills(account);
        for (Skill one : skills) {
            if (one.getName().equals(skill)) {
                exists = true;
            }
        }
        
        return exists;
    }
    
    public List <Skill> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills;
    }
    
    public List <Skill> getAllUserSkills(Account account) {
        List<Skill> skills = account.getSkills();
        return skills;
    }
    
    public Skill createNewSkill(String skill_name) {
        List <Account> pros = new ArrayList<>();
        Skill new_skill = new Skill(skill_name, pros);
        return new_skill;
    }
    
    
    @Transactional
    public void addNewSkill(Account account, String skill) {
        String skill_trimmed = trim_skill(skill);
        if (skill_trimmed == null) {
            //do nothing
        } else {
        boolean is = accountHasSkill(account, skill_trimmed);
        if (!is) {
            Skill existing_skill = skillRepository.findByName(skill_trimmed);
            if (existing_skill == null) {
                Skill new_skill = createNewSkill(skill_trimmed);
                account.getSkills().add(new_skill);
                accountRepository.save(account);
            } else {
                account.getSkills().add(existing_skill);
                accountRepository.save(account);
            }
        }
        }
    }
    
    @Transactional
    public void deleteOldSkill(Account account, Long skill_id) {
        List <Skill> skills = account.getSkills();
        
        for (Skill skill : skills) {
            if (skill.getId().equals(skill_id)) {
                skills.remove(skill);
                break;
            }
        }
        
        accountRepository.save(account);
    }
}
