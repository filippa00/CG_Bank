package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.dto.AccountSearchRequestDTO;
import io.swagger.model.User;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.dto.AccountSearchDTO;
import io.swagger.model.dto.AccountSettingsDTO;
import io.swagger.model.dto.BalanceDTO;
import io.swagger.model.enums.AccountType;
import io.swagger.model.enums.Currency;
import io.swagger.model.enums.Role;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.AccountSearchRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountSearchRepository accountSearchRepository;

    public List<Account> getAccountsEmployee() {
        return accountRepository.getAllAccountsForUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public List<Account> getAllAccounts() {

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            return accountRepository.getAllAccountsForUserId(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        }

        return accountRepository.getAllAccounts();
    }

    public Account createAccount(AccountDTO dto) {

        if (dto.getUserid() == 1) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cant open an account for the bank itself");
        }

        if (!String.valueOf(dto.getType()).equals(String.valueOf(AccountType.SAVINGS)) && !String.valueOf(dto.getType()).equals(String.valueOf(AccountType.CURRENT))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Account type is not valid");
        }

        if (dto.getAbsoluteLimit() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is limited with negative amount");
        }

        if (userRepository.findUserById(dto.getUserid()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant open an account for a user that doesnt exist");
        }

        Account newAccount = new Account();

        newAccount.setUserid(dto.getUserid());
        newAccount.setIban(generateIBAN());
        while (accountRepository.getAccountByIban(newAccount.getIban()) != null) {
            newAccount.setIban(generateIBAN());
        }
        newAccount.setCurrency(Currency.EURO);
        newAccount.setBalance(0.0);
        newAccount.setActive(true);
        newAccount.setPincode(new Random().nextInt(9000) + 1000);
        newAccount.setAbsoluteLimit(dto.getAbsoluteLimit());
        newAccount.setType(dto.getType());

        return accountRepository.save(newAccount);
    }

    public BalanceDTO getAccountBalance(String iban) {

        validateIban(iban);

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(accountRepository.getBalance(iban));
        return balanceDTO;
    }

    public Account add(Account account) {
        return accountRepository.save(account);
    }

    public void activate(String iban, Boolean activate) {

        validateIban(iban);

        if (accountRepository.getAccountByIban(iban).getActive() == activate) {
            if (activate) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is already active");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is already locked");
            }
        }
        accountRepository.activate(iban, activate);
    }

    public AccountSettingsDTO getAccountSettingsByIban(String iban) {

        validateIban(iban);

        Account account = accountRepository.getAccountByIban(iban);
        AccountSettingsDTO accountSettingsDTO = new AccountSettingsDTO();

        accountSettingsDTO.setAbsoluteLimit(account.getAbsoluteLimit());
        accountSettingsDTO.setPincode(account.getPincode());
        return accountSettingsDTO;
    }

    public void updateSettings(AccountSettingsDTO dto, String iban) {

        validateIban(iban);

        if (String.valueOf(dto.getPincode()).length() != 4) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid pincode, must be 4 digits");
        }

        if (dto.getAbsoluteLimit() > 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Absolute limit refers to negative amount");
        }

        accountRepository.updateSettings(iban, dto.getPincode(), dto.getAbsoluteLimit());
    }

    //    3. Account numbers must adhere to IBAN standards, the format: NLxxINHO0xxxxxxxxx
    private String generateIBAN() {
        Random random = new Random();
        String prefix = "NL";
        String midfield = "INHO0";
        String iban = prefix;

        for (int i = 0; i < 2; i++) {
            int number = random.nextInt(10);
            iban += Integer.toString(number);
        }
        iban += midfield;
        for (int i = 0; i < 9; i++) {
            int number = random.nextInt(10);
            iban += number;
        }
        return iban;
    }

    private void validateIban(String iban) {

        if (iban.equals("NL01INHO0000000001")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cant access this account (bank)");
        }

        if (accountRepository.getAccountByIban(iban) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cant find this account");
        }

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            if (!userRepository.getUserByIban(iban).getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Customer can only access his own accounts");
            }
        }

    }

//    public List<AccountSearchDTO> getAccountByName(String lastName, String firstName) {
//        if (!lastName.equals("")) {
//            List<Account> accounts = accountRepository.getAccountByName(lastName, firstName);
//            List<AccountSearchDTO> accountdtos = new ArrayList<>(accounts.size());
//            for (Account account : accounts) {
//
//                AccountSearchDTO dto = new AccountSearchDTO();
//                dto.setType(account.getType());
//                dto.setIban(account.getIban());
//                accountdtos.add(dto);
//            }
//            return accountdtos;
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to enter the lastname");
//        }
//    }

    public List<AccountSearchDTO> getAccountByName(AccountSearchRequestDTO request) {
        if (!request.equals("")) {
           List<User>users = accountSearchRepository.getAccountByName(request);
           List<AccountSearchDTO> accountSearchDTOS = new ArrayList<>();

            for (User user : users) {
                List<Account> accounts = accountRepository.getAccountByUserId(user.getId());
                for(Account account : accounts){
                    AccountSearchDTO accountSearchDTO = new AccountSearchDTO();
                    accountSearchDTO.setIban(account.getIban());
                    accountSearchDTO.setType(account.getType());
                    accountSearchDTOS.add(accountSearchDTO);
                }

            }
            return accountSearchDTOS;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to enter the lastname");
        }
    }


}


