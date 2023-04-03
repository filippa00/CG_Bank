package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.Account;
import io.swagger.model.dto.AccountSearchRequestDTO;
import io.swagger.model.dto.*;
import io.swagger.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")
@RestController
@Api(tags = "Accounts")
public class AccountApiController implements AccountApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private final AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request, AccountService accountService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.accountService = accountService;
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BalanceDTO> getAccountBalance(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {
        return ResponseEntity.ok().body(accountService.getAccountBalance(IBAN));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountSettingsDTO> getAccountSettings(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {
        return ResponseEntity.ok().body(accountService.getAccountSettingsByIban(IBAN));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> accountIBANSettingsPut(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody AccountSettingsDTO body) {
        accountService.updateSettings(body, IBAN);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AccountSearchDTO>> getAccountByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @Valid @RequestParam(value = "lastName", required = true) String lastName, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "firstName", required = false) String firstName) {
        AccountSearchRequestDTO accountSearchRequestDTO = new AccountSearchRequestDTO();
        accountSearchRequestDTO.setFirstName(firstName);
        accountSearchRequestDTO.setLastName(lastName);
        return ResponseEntity.ok().body(accountService.getAccountByName(accountSearchRequestDTO));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<ActivateDTO> activateAccount(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "Activate or lock an account", required = true, schema = @Schema()) @Valid @RequestBody ActivateDTO body) {
        accountService.activate(IBAN, body.getActivate());
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<Account> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "Account object to be created", required = true, schema = @Schema()) @Valid @RequestBody AccountDTO body) {
        return ResponseEntity.status(201).body(accountService.createAccount(body));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Account>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @Override
    public ResponseEntity<List<Account>> getAccountsEmployee() {
        return ResponseEntity.ok().body(accountService.getAccountsEmployee());
    }

}
