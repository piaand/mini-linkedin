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
    private String post = "Ibsum lorem";
    private String comment = "bacon ibsum comm";
    
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
    
    public void testCommentPost() {
        find("div.comment-form textarea").first().fill().with(comment);
        find("button", withText("Post comment")).first().click();
        assertTrue(pageSource().contains(comment));
        System.out.print(pageSource());
        assertTrue(pageSource().contains("commented:"));
    }
    
    public void testPublishPost() {
        find("a", withText("Board")).click();
        assertTrue(pageSource().contains("Write a new post to your network"));
        assertTrue(pageSource().contains("Your network has been quiet."));
        find("textarea.form-control").first().fill().with(post);
        find("button", withText("Post")).first().click();
        assertTrue(pageSource().contains(post));
        System.out.print(pageSource());
        assertTrue(pageSource().contains(name2));
        assertTrue(pageSource().contains("posted:"));
        testCommentPost();
    }
    
    public void testFindUser(String name) {
        find("input").first().fill().with(name);
        find("#hidden-enter").submit();
        assertTrue(pageSource().contains(name));
    }
    
    public void testVoteSkill() {
        testFindUser(name1);
        assertTrue(pageSource().contains("Endorse"));
        find(".btn-sm").first().click();
        assertTrue(pageSource().contains("Endorse"));
        System.out.print(pageSource());
    }
    
    public void testConnectUser() {
        testFindUser(name2);
        find("button", withText("Add this user to your contact network")).first().click();
        find("#logout-button").click();
        assertTrue(pageSource().contains("Log in"));
        
        /* Login user2*/
        find(".btn-default", withText("Log in")).click();
        assertFalse(pageSource().contains(landingIntro));
        assertTrue(pageSource().contains(loginText));
        find("#username").fill().with(username2);
        find("#password").fill().with(password2);
        find("button").click();
        assertTrue(pageSource().contains("Accept as contact"));
        find("button", withText("Accept as contact")).click();
        assertTrue(pageSource().contains("Contact list"));
        assertTrue(pageSource().contains(name1));
        assertTrue(pageSource().contains("Delete contact"));
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
        find("button", withText("Back to your profile page")).first().click();
        assertTrue(pageSource().contains("Alter your"));
        assertTrue(pageSource().contains(skill1));
        assertTrue(pageSource().contains(skill2));
        assertFalse(pageSource().contains(skill3));
        
    }
    
    @Test
    public void testAlterProfile() {
        assertTrue(pageSource().contains("No skills added"));
        find("#alter-profile-button").click();
        assertTrue(pageSource().contains("You haven't listed any skills!"));
        testAddSkills();
        testConnectUser();
        testVoteSkill();
        testPublishPost();
    }
    

}
