package io.swagger.api.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration
public class CucumberContextConfig {

    @Before
    public void context(){

    }
}
