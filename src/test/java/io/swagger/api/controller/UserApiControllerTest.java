package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.User;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.dto.UserUpdateDTO;
import io.swagger.model.enums.Role;
import io.swagger.repository.UserRepository;
import io.swagger.secutiry.JwtTokenFilter;
import io.swagger.secutiry.JwtTokenProvider;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);


    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getAllUsers() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getTotalBalance() throws Exception {
        this.mockMvc.perform(get("/user/2/totalBalance"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
    public void getUsersWithoutAccount() throws Exception {
        this.mockMvc.perform(get("/user/noAccount"))
                .andExpect(status().isOk());
    }


//    @Test
//    @WithMockUser(username = "user2", password = "123", roles = "EMPLOYEE")
//    public void createUser() throws Exception {
//        User user = new User();
//        //user.setId(10L);
//        user.setUsername("nkdl");
//        user.setPassword("12345");
//        user.setFirstname("fdgd");
//        user.setLastname("sfs");
//        user.setRole(Role.ROLE_CUSTOMER);
//        user.setDayLimit(500.00);
//        user.setTransactionLimit(200.00);
//        when(userService.createUser(any(User.class))).thenReturn(user);
//        this.mockMvc.perform(post("/user")
//                .content(mapper.writeValueAsString(user))
//                .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//    }

    @Test
    @WithMockUser(username = "user2", password = "123", roles = "CUSTOMER")
    public void updateUser() throws Exception {
        UserUpdateDTO user = new UserUpdateDTO();
        doThrow(new IllegalArgumentException()).when(userService).updateUser(user);
    }
}