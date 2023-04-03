package io.swagger.api.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.bs.A;
import io.cucumber.java8.En;
import io.swagger.model.Account;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.dto.BalanceDTO;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.enums.AccountType;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class AccountStepDefinition extends BaseStepDefinitions implements En {

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private String token;
    private Integer status;
    private AccountDTO accountDTO;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;


    public AccountStepDefinition() {
        When("^I call the account endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/account", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });
        When("^I call the /account/IBAN/balance endpoint$", () -> {
            //BalanceDTO balanceDTO = accountService.getAccountBalance("IBAN1CURRENT");
            httpHeaders.clear();
            httpHeaders.add("Authorization", "Bearer " + token);
            //request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "account/IBAN1CURRENT/balance", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        Then("^the result is a list of accounts of size (\\d+)$", (Integer size) -> {
            int actual = JsonPath.read(response.getBody(), "$.size()");
            int newSize = size;
            Assertions.assertEquals(newSize, actual);
        });

        And("^I have a valid account object id \"([^\"]*)\", type \"([^\"]*)\", absoluteLimit \"([^\"]*)\"$", (Long arg0, AccountType arg1, Double arg2) -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setType(arg1);
            accountDTO.setUserid(arg0);
            accountDTO.setAbsoluteLimit(arg2);
        });

        When("^I make a post request to the account endpoint$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Content-Type", "application/json");
            request = new HttpEntity<>(mapper.writeValueAsString(accountDTO), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/account", request, String.class);
            status = response.getStatusCodeValue();
        });

    }
}
