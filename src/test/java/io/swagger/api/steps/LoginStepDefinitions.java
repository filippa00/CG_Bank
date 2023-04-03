package io.swagger.api.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.api.steps.BaseStepDefinitions;
import io.swagger.model.dto.LoginDTO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Slf4j
public class LoginStepDefinitions extends BaseStepDefinitions implements En {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private Integer status;

    private LoginDTO dto;

    public LoginStepDefinitions() {
        Given("^I have a valid user object$", () -> {
            dto = new LoginDTO("user2", "123");
        });
        When("^I call the login endpoint$", () -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(
                    dto),
                    httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/login",
                    request, String.class);
        });

        Then("^I receive a status of (\\d+)$", (Integer status) -> {
            int newInt = status;
            Assertions.assertEquals(newInt, response.getStatusCodeValue());
        });

        And("^I get a JWT-token$", () -> {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String token = jsonObject.getString("token");
            Assertions.assertTrue(token.startsWith("ey"));
        });

        Given("^I have an invalid user object$", () -> {
            dto = new LoginDTO("user", "120");
        });
    }
}
