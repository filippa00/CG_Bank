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

    public User getUserEmployee () {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public List<User> getAllUsers() {

        // if is a customer show only himself
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            List<User> user = new ArrayList<>();
            user.add(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            return user;
        }

        return userRepository.getAllUsers();
    }

    public List<User> getUsersWithoutAccount() {

        return userRepository.getUsersWithoutAccount();
    }

    public User createUser(UserDTO dto) {

        if (dto.getUsername() == null || dto.getFirstname() == null || dto.getLastname() == null || dto.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Fill in all required fields");
        }
        if (dto.getUsername().isEmpty() || dto.getFirstname().isEmpty() || dto.getLastname().isEmpty() || dto.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Fill in all required fields");
        }
        // check if first and last name are all letter
        if (!Pattern.matches("[a-zA-Z]+", dto.getFirstname()) || !Pattern.matches("[a-zA-Z]+", dto.getLastname())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Name can only contain letters");
        }

        // check the there is not another user like this
        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setRole(Role.ROLE_CUSTOMER);
        user.setDayLimit(2500.0);
        user.setTransactionLimit(500.0);

        return userRepository.save(user);
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
