package io.swagger.service;

import io.swagger.model.User;
import io.swagger.model.dto.*;
import io.swagger.model.enums.Role;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import io.swagger.secutiry.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDTO getUserEmployee () {
        return userToDTO(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    public List<UserDTO> getAllUsers() {

        List<UserDTO> dtos = new ArrayList<>();
        
        // if is a customer show only himself
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            dtos.add(userToDTO(user));
            return dtos;
        }
        
        for (User user : userRepository.getAllUsers()) {
            dtos.add(userToDTO(user));
        }
        return dtos;
    }

    private UserDTO userToDTO(User user){
//        List<UserDTO> dtos = new ArrayList<>();

//        for (User user : users) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setFirstname(user.getFirstname());
            dto.setLastname(user.getLastname());
            dto.setRole(user.getRoles().get(0));
            dto.setDayLimit(user.getDayLimit());
            dto.setTransactionLimit(user.getTransactionLimit());
//            dtos.add(dto);
//        }

        return dto;
    }

    public List<UserDTO> getUsersWithoutAccount() {
        List<UserDTO> dtos = new ArrayList<>();
        for (User user : userRepository.getUsersWithoutAccount()) {
            dtos.add(userToDTO(user));
        }
        return dtos;
    }

    public UserDTO createUser(User user) {

        if (user.getUsername() == null || user.getFirstname() == null || user.getLastname() == null || user.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Fill in all required fields");
        }
        if (user.getUsername().isEmpty() || user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Fill in all required fields");
        }
        // check if first and last name are all letter
        if (!Pattern.matches("[a-zA-Z]+", user.getFirstname()) || !Pattern.matches("[a-zA-Z]+", user.getLastname())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Name can only contain letters");
        }

        // check the there is not another user like this
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exists");
        }

        //hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set default settings and save user
//        user.setRole(Role.ROLE_CUSTOMER);
        user.setDayLimit(2500.0);
        user.setTransactionLimit(500.0);
        userRepository.save(user);

//        UserDTO dto = new UserDTO();
//        dto.setId(user.getId());
//        dto.setUsername(user.getUsername());
//        dto.setFirstname(user.getFirstname());
//        dto.setLastname(user.getLastname());
//        dto.setRole(Role.ROLE_CUSTOMER);
//        dto.setDayLimit(user.getDayLimit());
//        dto.setTransactionLimit(user.getTransactionLimit());

        return userToDTO(user);
    }

    public BalanceDTO getTotalBalance(Long userId) {

        userAccess(userId);

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(userRepository.getTotalBalance(userId));
        return balanceDTO;
    }

    public void updateUser(UserUpdateDTO dto) {

        User updatedUser = userRepository.findByUsername(dto.getUsername());
        User performing = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (updatedUser != null) {
            if(performing.getRoles().contains(Role.ROLE_CUSTOMER)){
                if (updatedUser != performing ) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "New username already exists in the system");
                }
            }

        }

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            if (updatedUser != performing) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Customers make changes on themselves");
            }
        }

        if (dto.getUsername() == null || dto.getPassword() == null || dto.getUsername().isEmpty() || dto.getPassword().isEmpty() || dto.getDayLimit() == null || dto.getTransactionLimit() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Fill in all required fields");
        }

        if (dto.getDayLimit() < 0 || dto.getTransactionLimit() < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Limits must be bigger than 0");
        }

        if (dto.getDayLimit() < dto.getTransactionLimit()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Day limit must be greater than transaction limit");
        }

        User toBeChanged = userRepository.findByUsername(dto.getUsername());
        if (toBeChanged == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cant find this user");
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.updateUser(SecurityContextHolder.getContext().getAuthentication().getName(),
                dto.getUsername(), dto.getPassword(), dto.getDayLimit(), dto.getTransactionLimit());
    }

    public LoginResponseDTO login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            //set hash password
            //return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken(jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles()));
            loginResponseDTO.setEmployee(userRepository.findByUsername(username).getRoles().contains(Role.ROLE_EMPLOYEE));
            loginResponseDTO.setAccnountOwner(accountRepository.getAllAccountsForUsername(username) != null);
            return loginResponseDTO;
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password");
        }
    }

    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public LimitsDTO getLimits(Long id) {

        userAccess(id);

        User user = userRepository.findUserById(id);
        LimitsDTO dto = new LimitsDTO();
        dto.setDayLimit(user.getDayLimit());
        dto.setTransactionLimit(user.getTransactionLimit());
        Double used = userRepository.getDailyUsed(user.getId(), LocalDate.now());
        dto.setDailyRemaining(user.getDayLimit() - used);

        return dto;
    }

    private void userAccess(Long userId) {

        if (userId == userRepository.getUserByIban("NL01INHO0000000001").getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cant access this user (bank)");
        }

        if (userRepository.findUserById(userId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cant find this user");
        }

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            User customer = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (userId != customer.getId()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cant access another user's balance");
            }
        }
    }
}
