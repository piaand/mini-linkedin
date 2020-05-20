/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import java.util.ArrayList;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import java.nio.file.AccessDeniedException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
/**
 *
 * @author piaandersin
 */

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private SkillVoteRepository skillVoteRepository;
    
    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    private String trim_skill(String skill) {
        String trimmed;
        
        if ((skill == null) || !(skill.trim().length() > 0))  {
            return null;
        } else {
            trimmed = skill.trim().toLowerCase();
            return trimmed;
        }
    }
    
    private boolean accountHasSkill(Account account, Skill skill) {
        
        boolean exists = false;
        
        List <SkillVote> skills = getAllUserSkills(account);
        for (SkillVote one : skills) {
            if (one.getSkillId().equals(skill.getId())) {
                exists = true;
            }
        }
        return exists;
    }
    
    @Transactional
    public Skill createNewSkill(String skill_trimmed) {
        Skill skill = new Skill(skill_trimmed);
        skillRepository.save(skill);
        return skill;
    }
    
    public Skill getDBSkill(String skill_trimmed) {
        Skill skill = skillRepository.findByName(skill_trimmed);
        
        if (skill == null) {
            Skill new_skill = createNewSkill(skill_trimmed);
            return new_skill;
        } else {
            return skill;
        }
    }
    
    @Transactional
    public void addNewSkillVote(Account account, Skill skill){
        skillVoteRepository.save(new SkillVote(account, skill.getId(), skill.getName(), 0L));
    }
    
    public List <Skill> getAllSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills;
    }
    
    public List <SkillVote> getAllUserSkills(Account account) {
        List<SkillVote> profileSkills = account.getVoted_skills();
        return profileSkills;
    }
    
    public List <SkillVote> getAllSortedUserSkills(Account account) {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("upvotes").descending());
        return skillVoteRepository.findByTalent(account, pageable);
    }
    
    @PreAuthorize("#account.username == authentication.principal.username")
    public void addNewSkill(Account account, String skill_name) {
        String skill_trimmed = trim_skill(skill_name);
        if (skill_trimmed == null) {
            //do nothing
        } else {
            Skill skill = getDBSkill(skill_trimmed);
            boolean has = accountHasSkill(account, skill);
            if (!has) {
                addNewSkillVote(account, skill);
            }
        }
    }
    
    @PreAuthorize("#account.username == authentication.principal.username")
    @Transactional
    public void deleteProfileSkill(Account account, Long skill_id) {
        
        SkillVote skill = skillVoteRepository.findByTalentAndSkillId(account, skill_id);
        skillVoteRepository.delete(skill);
        
    }
    
    public boolean hasAccountVoted(Vote vote) {
        Vote used_vote = voteRepository.findByTargetSkillAndTargetProfileAndVoter(vote.getTargetSkill(), vote.getTargetProfile(), vote.getVoter());

        if (used_vote == null) {
            return false;
        } else {
            return true;
        }
    }
    
    @PreAuthorize("#voter.username == authentication.principal.username")
    @Transactional
    public void upvoteSkill(Account voter, String targetProfile, Long skill_id) {
        Vote vote = new Vote(voter, targetProfile, skill_id);
        boolean hasVoted = hasAccountVoted(vote);
        
        if (!hasVoted) {
            Account talent = accountRepository.findByProfile(targetProfile);
            SkillVote voted = skillVoteRepository.findByTalentAndSkillId(talent, skill_id);
            voted.setUpvotes(voted.getUpvotes() + 1);
            skillVoteRepository.save(voted);
            voteRepository.save(vote);
        }
        
    }
}
