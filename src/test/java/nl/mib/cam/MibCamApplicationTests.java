package nl.mib.cam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MibCamApplication.class)
//@WebAppConfiguration
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class MibCamApplicationTests {

    @Test
    public void contextLoads() {
        //com.tngtech.jgiven.

    }

}
