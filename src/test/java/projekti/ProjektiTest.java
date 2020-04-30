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
}
