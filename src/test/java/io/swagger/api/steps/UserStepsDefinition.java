package io.swagger.api.steps;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.swagger.model.User;
import io.swagger.model.dto.BalanceDTO;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.dto.UserUpdateDTO;
import io.swagger.model.enums.Role;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

public class UserStepsDefinition extends BaseStepDefinitions implements En {

    // Token is valid for one year (taken from Wim's class)
    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifV0sImlhdCI6MTY1NTg1MDYzMCwiZXhwIjoxNjU1ODU0MjMwfQ.FZDALAgnsf4nMG_qqOfIU31BUlezrW4vynANFrIQX7Q";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfRU1QTE9ZRUUifV0sImlhdCI6MTY1NTgzODU2NCwiZXhwIjoxNjU1ODQyMTY0fQ.W5Htj_iFwNXfSFJ6f0ojl_LTJVRbMUa8OWsWNyTUHgI";
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6W10sImlhdCI6MTY1MzMxMTkwNSwiZXhwIjoxNjUzMzExOTA1fQ.mKFrXM15WCXVNbSFNpqYix_xsMjsH_M31hiFf-o7JXs";
    private static final String INVALID_TOKEN = "invalid";
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private String token;
    private Integer status;
    private User user;
    private UserDTO userDTO;
    private BalanceDTO balanceDTO;
    private UserUpdateDTO userUpdateDTO;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public UserStepsDefinition() {
        Given("^I have a valid token for the role employee$", () -> {
            token = VALID_TOKEN_ADMIN;

        });

        Given("^I have a valid token for the role customer$", () -> {
            token = VALID_TOKEN_USER;
            });

        Given("^I have an invalid token$", () -> {
            token = INVALID_TOKEN;
        });

        When("^I call the user endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            httpHeaders.add("Content-Type", "application/json");
            response = restTemplate.exchange(getBaseUrl() + "/user", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        Given("^I have an expired token$", () -> {
            token = EXPIRED_TOKEN;
        });

        When("^I make a post request to the user endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Content-Type", "application/json");
            request = new HttpEntity<>(mapper.writeValueAsString(userDTO), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/user", request, String.class);
            status = response.getStatusCodeValue();
        });

        When("^I call the user/noAccount endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            httpHeaders.add("Content-Type", "application/json");
            response = restTemplate.exchange(getBaseUrl() + "/user/noAccount", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        When("^I call the user/userId/totalBalance endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            httpHeaders.add("Content-Type", "application/json");
            response = restTemplate.exchange(getBaseUrl() + "/user/2/totalBalance", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        Then("^the result is a status of (\\d+)$", (Integer expected) -> {
            int status = expected;
            Assertions.assertEquals(status, response.getStatusCodeValue());
        });

        And("^I have a valid user object username \"([^\"]*)\", password \"([^\"]*)\", firstname \"([^\"]*)\", lastname \"([^\"]*)\"$", (String username, String password, String firstname, String lastname) -> {
            userDTO = new UserDTO();
            userDTO.setFirstname(firstname);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setLastname(lastname);
        });

        When("^I call the getAll user endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            httpHeaders.add("Content-Type", "application/json");
            response = restTemplate.exchange(getBaseUrl() + "/user", HttpMethod.GET, new HttpEntity<>(user, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });
        When("^I make a put request to the user endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            httpHeaders.add("Content-Type", "application/json");
            response = restTemplate.exchange(getBaseUrl() + "/user", HttpMethod.PUT, new HttpEntity<>(userUpdateDTO, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });
        And("^I have a valid user object username \"([^\"]*)\", password \"([^\"]*)\", dayLimit \"([^\"]*)\", transactionLimit \"([^\"]*)\"$", (String username, String password, Double dayLimit, Double transactionLimit) -> {

                UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
            //User user = userRepository.findByUsername(userUpdateDTO.getUsername());
                userUpdateDTO.setUsername(username);
                userUpdateDTO.setPassword(password);
                userUpdateDTO.setDayLimit(dayLimit);
                userUpdateDTO.setTransactionLimit(transactionLimit);
                userService.updateUser(userUpdateDTO);
        });
    }
}
