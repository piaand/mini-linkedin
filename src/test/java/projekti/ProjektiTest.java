package projekti;

import org.junit.Test;
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

    @LocalServerPort
    Integer port;
    
    @Test
    public void testlangingPage() {
        String landingIntro = "The Only Spike-friendly Resume App";
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains(landingIntro));
        find("a", withText("About")).click();
        assertFalse(pageSource().contains(landingIntro));
    }
    
    @Test
    public void testRegistration() {
        String landingIntro = "The Only Spike-friendly Resume App";
        String signupText = "I solemnly swear that I am up to no good";
        String name = "Matti Meikäläinen";
        String username = "mattis";
        String password = "kissa";
        String profile = "matti-meikalainen-1";
        
        goTo("http://localhost:" + port);
        assertTrue(pageSource().contains(landingIntro));
        find(".btn-default", withText("Sign up")).click();
        assertTrue(pageSource().contains(signupText));
        find("input").fill().with(name, username, password, profile);
        find("button", withText("Submit")).click();
        assertTrue(pageSource().contains(landingIntro));
        find(".btn-lg", withText("Log in")).click();
        assertFalse(pageSource().contains(landingIntro));
        find("input").fill().with(username, password);
        find("button").click();
        assertTrue(pageSource().contains("Secret!"));
        assertEquals(false, true);
    }
}
