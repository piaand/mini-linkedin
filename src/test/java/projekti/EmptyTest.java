package projekti;

import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.fluentlenium.configuration.FluentConfiguration;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import org.springframework.boot.web.server.LocalServerPort;
import static org.fluentlenium.core.filter.FilterConstructor.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmptyTest extends org.fluentlenium.adapter.junit.FluentTest {
    
    private String landingIntro = "The Only Spike-friendly Resume App";
    private String signupText = "Fill all the fields between 2 to 50 characters";
    private String loginText = "Please sign in";
    private String name1 = "Matti Meikalainen";
    private String username1 = "mattis";
    private String password1 = "kissa";
    private String profile1 = "matti-meikalainen-1";
    private String skill1 = "napping";
    private String skill2 = "foraging";
    
    @LocalServerPort
    Integer port;
    
    @Test
    public void testInit() {
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains(landingIntro));
    }
    
    @Test
    public void testLoginFromLanding() {
        testInit();
        find(".btn-default", withText("Log in")).click();
        assertFalse(pageSource().contains(landingIntro));
        assertTrue(pageSource().contains(loginText));
        find("#username").fill().with(username1);
        find("#password").fill().with(password1);
        find("button").click();
        System.out.print(pageSource());
        assertTrue(pageSource().contains("Alter your"));
    }

    @Test
    public void testlangingPage() {
        testInit();
        assertFalse(pageSource().contains("Board"));
        find("a", withText("About")).click();
        assertFalse(pageSource().contains(landingIntro));
    }
    
    @Test
    public void testRegistration() {
        testInit();
        find(".btn-default", withText("Sign up")).click();
        assertTrue(pageSource().contains(signupText));
        find("#inputFullname").fill().with(name1);
        find("#inputUsername").fill().with(username1);
        find("#inputPassword1").fill().with(password1);
        find("#inputProfileChar").fill().with(profile1);
        find("button", withText("Submit")).click();
        assertTrue(pageSource().contains(landingIntro));
        testLoginFromLanding();
        find("#logout-button").click();
        assertTrue(pageSource().contains("Log in"));
    }
     
}
