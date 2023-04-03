package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.dto.AccountSettingsDTO;
import io.swagger.model.enums.AccountType;
import io.swagger.model.enums.Currency;
import io.swagger.model.enums.Role;
import io.swagger.repository.AccountRepository;
import io.swagger.secutiry.JwtTokenProvider;
import io.swagger.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AccountService accountService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getAllAccounts() throws Exception {
        this.mockMvc.perform(get("/account"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "123", roles = "CUSTOMER")
    public void getAccountBalance() throws Exception {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)){
            accountService.getAccountBalance("IBAN2SAVINGS");
            this.mockMvc.perform(get("/account/IBAN2SAVINGS/balance"))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getAccountSettings() throws Exception {
        this.mockMvc.perform(get("/account/IBAN2SAVINGS/settings"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void createAccount() throws Exception {
        Account newAccount = new Account();
        newAccount.setUserid(10L);
        newAccount.setIban("IBAN10CURRENT");
        newAccount.setType(AccountType.CURRENT);
        newAccount.setActive(true);
        newAccount.setBalance(2000.00);
        newAccount.setAbsoluteLimit(-800.00);
        newAccount.setPincode(4569);
        newAccount.setCurrency(Currency.EURO);
        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(newAccount);
        this.mockMvc.perform(post("/account")
                        .content(mapper.writeValueAsString(newAccount))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void updateAccountSettings() throws Exception {
        AccountSettingsDTO accountSettingsDTO = new AccountSettingsDTO();
        doThrow(new IllegalArgumentException()).when(accountService).updateSettings(accountSettingsDTO, "IBAN2SAVINGS");
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getAccountByName() throws Exception {
        this.mockMvc.perform(get("/account/search?lastName=last2&firstName=first2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void activateAccount() throws Exception {
        doThrow(new IllegalArgumentException()).when(accountService).activate("IBAN2SAVINGS", true);
    }
}