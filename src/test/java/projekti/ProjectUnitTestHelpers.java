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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
 
 
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectUnitTestHelpers {
    
    @Autowired
    SignupService signupService;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    SkillService skillService;
    /*
    @Test
    public void testTrimming() {
        String skill1_name = "Cleaning";
        String skill2_name = "napping";
        String skill3_name = "  cleaning";
        String skill4_name = " naPPing       ";
        String skill5_name = "       ";
        String skill6_name = "";
        String skill7_name = null;
        String skill;
        
        
        //trim_skill need to be set to public for this
        skill = skillService.trim_skill(skill2_name);
        assertTrue(skill2_name.equals(skill2_name));
        assertNotNull(skill);
        skill = skillService.trim_skill(skill4_name);
        assertNotNull(skill);
        //assertTrue(skill2_name.equals(skill4_name));
        
        skill = skillService.trim_skill(skill5_name);
        assertNull(skill);
        skill = skillService.trim_skill(skill6_name);
        assertNull(skill);
        skill = skillService.trim_skill(skill7_name);
        assertNull(skill);
        
        skill = skillService.trim_skill(skill1_name);
        assertNotNull(skill);
        skill = skillService.trim_skill(skill3_name);
        assertNotNull(skill);
        //assertTrue(skill1_name.equals(skill3_name));
        
    }
*/
}
