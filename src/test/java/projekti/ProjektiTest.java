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
public class ProjektiTest extends org.fluentlenium.adapter.junit.FluentTest {
    
    private String landingIntro = "The Only Spike-friendly Resume App";
    private String signupText = "Fill all the fields between 2 to 50 characters";
    private String loginText = "Please sign in";
    private String name = "Matti Meikalainen";
    private String username = "mattis";
    private String password = "kissa";
    private String profile = "matti-meikalainen-1";
    
    @LocalServerPort
    Integer port;
    
    @Test
    public void testlangingPage() {
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains(landingIntro));
        find("a", withText("About")).click();
        assertFalse(pageSource().contains(landingIntro));
    }
    
    @Test
    public void testRegistration() {
        
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains(landingIntro));
        find(".btn-default", withText("Sign up")).click();
        assertTrue(pageSource().contains(signupText));
        find("#inputFullname").fill().with(name);
        find("#inputUsername").fill().with(username);
        find("#inputPassword1").fill().with(password);
        find("#inputProfileChar").fill().with(profile);
        find("button", withText("Submit")).click();
        assertTrue(pageSource().contains(landingIntro));
        find(".btn-lg", withText("Log in")).click();
        assertFalse(pageSource().contains(landingIntro));
        assertTrue(pageSource().contains(loginText));
        find("#username").fill().with(username);
        find("#password").fill().with(password);
        find("button").click();
        assertTrue(pageSource().contains("Alter your"));
    }
    
}
