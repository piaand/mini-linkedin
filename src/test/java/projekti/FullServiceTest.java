/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author piaandersin
 */


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullServiceTest extends org.fluentlenium.adapter.junit.FluentTest {
    private String landingIntro = "The Only Spike-friendly Resume App";
    private String signupText = "Fill all the fields between 2 to 50 characters";
    private String loginText = "Please sign in";
    private String name1 = "Matti Meikalainen";
    private String username1 = "mattis";
    private String password1 = "kissa";
    private String profile1 = "matti-meikalainen-1";
    private String name2 = "Jez Jerzy";
    private String username2 = "jezzz";
    private String password2 = "snob";
    private String profile2 = "jez-jerzy";
    private String skill1 = "napping";
    private String skill2 = "foraging";
    private String skill3 = "polish";
    
    @LocalServerPort
    Integer port;
    
    @Before
    public void setup() {
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains(landingIntro));
        
        /* Create user1 */
        find(".btn-default", withText("Sign up")).click();
        assertTrue(pageSource().contains(signupText));
        find("#inputFullname").fill().with(name1);
        find("#inputUsername").fill().with(username1);
        find("#inputPassword1").fill().with(password1);
        find("#inputProfileChar").fill().with(profile1);
        find("button", withText("Submit")).click();
        assertTrue(pageSource().contains(landingIntro));
        
        /* Create user2 */
        find(".btn-default", withText("Sign up")).click();
        assertTrue(pageSource().contains(signupText));
        find("#inputFullname").fill().with(name2);
        find("#inputUsername").fill().with(username2);
        find("#inputPassword1").fill().with(password2);
        find("#inputProfileChar").fill().with(profile2);
        find("button", withText("Submit")).click();
        assertTrue(pageSource().contains(landingIntro));
    }
    
    @Before
    public void testLogin() {
        find(".btn-default", withText("Log in")).click();
        assertFalse(pageSource().contains(landingIntro));
        assertTrue(pageSource().contains(loginText));
        find("#username").fill().with(username1);
        find("#password").fill().with(password1);
        find("button").click();
        System.out.print(pageSource());
        assertTrue(pageSource().contains("Alter your"));
    }
    
    public void testConnectUser() {
        
    }
    
    public void testAddSkills() {
        find("#add-new-skill > input").fill().with(skill1);
        find("#add-new-skill > button").click();
        assertTrue(pageSource().contains(skill1));
        assertTrue(pageSource().contains("Delete this skill"));
        find("#add-new-skill > input").fill().with(skill2);
        find("#add-new-skill > button").click();
        assertTrue(pageSource().contains(skill1));
        assertTrue(pageSource().contains(skill2));
        assertFalse(pageSource().contains(skill3));
        find("#add-new-skill > input").fill().with(skill3);
        find("#add-new-skill > button").click();
        assertTrue(pageSource().contains(skill3));
        System.out.print(pageSource());
        find("button", withText("Delete this skill")).first().click();
        assertFalse(pageSource().contains(skill3));
        
    }
    
    @Test
    public void testAlterProfile() {
        
        assertTrue(pageSource().contains("No skills added"));
        find("#alter-profile-button").click();
        assertTrue(pageSource().contains("You haven't listed any skills!"));
        testAddSkills();
        //testConnectUser();
    }
    

}
