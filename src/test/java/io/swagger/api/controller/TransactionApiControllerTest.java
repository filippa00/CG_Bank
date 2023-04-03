package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Transaction;
import io.swagger.model.dto.AtmDTO;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.repository.TransactionRepository;
import io.swagger.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper mapper;



    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getAllTransactions() throws Exception {
        this.mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getIbanTransaction() throws Exception {
        this.mockMvc.perform(get("/transaction/IBAN1SAVINGS"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void deposit() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(20.00);
        transaction.setExecutionDate(LocalDateTime.now());
        transaction.setUserPerforming(2L);
        transaction.setAccountTo("IBAN1CURRENT");
        transaction.setAccountFrom(null);
        when(transactionService.deposit(any(AtmDTO.class), eq("IBAN1CURRENT"))).thenReturn(transaction);
        this.mockMvc.perform(post("/transaction/IBAN1SAVINGS/deposit")
                .content(mapper.writeValueAsString(transaction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void withdraw() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(20.00);
        transaction.setExecutionDate(LocalDateTime.now());
        transaction.setUserPerforming(2L);
        transaction.setAccountTo(null);
        transaction.setAccountFrom("IBAN1CURRENT");
        when(transactionService.withdraw(any(AtmDTO.class), eq("IBAN1CURRENT"))).thenReturn(transaction);
        this.mockMvc.perform(post("/transaction/IBAN2SAVINGS/withdraw")
                .content(mapper.writeValueAsString(transaction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void transferTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setUserPerforming(1L);
        transaction.setAccountFrom("IBAN1CURRENT");
        transaction.accountTo("IBAN1SAVINGS");
        transaction.amount(260.23);
        transaction.executionDate(LocalDateTime.of(2022, 06, 11, 12, 00, 00));
        when(transactionService.add(any(TransactionDTO.class))).thenReturn(transaction);
        this.mockMvc.perform(post("/transaction")
                        .content(mapper.writeValueAsString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}