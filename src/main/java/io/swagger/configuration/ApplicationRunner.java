package io.swagger.configuration;

//import io.swagger.model.ATMTransaction;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.enums.AccountType;
import io.swagger.model.enums.Currency;
import io.swagger.model.enums.Role;
import io.swagger.repository.TransactionRepository;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Log
@Component

public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;



    public ApplicationRunner(UserService userService, AccountService accountService, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void run(ApplicationArguments args) {

        User bankOwner = new User();
        bankOwner.setId(1l);
        bankOwner.setUsername("bankOwner");
        bankOwner.setPassword("123");
        bankOwner.setRole(Role.ROLE_EMPLOYEE);
        bankOwner.setFirstname("bank");
        bankOwner.setLastname("owner");
        bankOwner.setPassword("090909");

        User user1 = new User();
        user1.setId(2l);
        user1.setUsername("user1");
        user1.setPassword("123");
        user1.setRole(Role.ROLE_CUSTOMER);
        user1.setFirstname("first1");
        user1.setLastname("last1");
        user1.setDayLimit(5000.00);
        user1.setTransactionLimit(200.00);

        User user2 = new User();
        user2.setId(3l);
        user2.setUsername("user2");
        user2.setPassword("123");
        user2.setRole(Role.ROLE_EMPLOYEE);
        user2.setFirstname("first2");
        user2.setLastname("last2");
        user2.setDayLimit(500.00);
        user2.setTransactionLimit(200.00);

        User user3 = new User();
        user3.setId(4l);
        user3.setUsername("user3");
        user3.setPassword("123");
        user3.setRole(Role.ROLE_CUSTOMER);
        user3.setFirstname("first3");
        user3.setLastname("last2");
        user3.setDayLimit(5000.00);
        user3.setTransactionLimit(200.00);


        List<User> users =
                List.of(
                        bankOwner, user1, user2,user3
                );

        users.forEach(
                userService::add
        );

        Account account1Current = new Account();
        account1Current.setUserid(2L);
        account1Current.setIban("IBAN1CURRENT");
        account1Current.setType(AccountType.CURRENT);
        account1Current.setActive(true);
        account1Current.setBalance(2000.00);
        account1Current.setAbsoluteLimit(-800.00);
        account1Current.setPincode(1234);
        account1Current.setCurrency(Currency.EURO);

        Account account1Savings = new Account();
        account1Savings.setUserid(2L);
        account1Savings.setIban("IBAN1SAVINGS");
        account1Savings.setType(AccountType.SAVINGS);
        account1Savings.setActive(true);
        account1Savings.setBalance(2000.00);
        account1Savings.setAbsoluteLimit(-800.00);
        account1Savings.setPincode(1234);
        account1Savings.setCurrency(Currency.EURO);

        Account account2Current = new Account();
        account2Current.setUserid(3L);
        account2Current.setIban("IBAN2CURRENT");
        account2Current.setType(AccountType.CURRENT);
        account2Current.setActive(true);
        account2Current.setBalance(2000.00);
        account2Current.setAbsoluteLimit(-800.00);
        account2Current.setPincode(1234);
        account2Current.setCurrency(Currency.EURO);

        Account account2Savings = new Account();
        account2Savings.setUserid(3L);
        account2Savings.setIban("IBAN2SAVINGS");
        account2Savings.setType(AccountType.SAVINGS);
        account2Savings.setActive(true);
        account2Savings.setBalance(2000.00);
        account2Savings.setAbsoluteLimit(-800.00);
        account2Savings.setPincode(1234);
        account2Savings.setCurrency(Currency.EURO);

        Account account3Current = new Account();
        account3Current.setUserid(4L);
        account3Current.setIban("IBAN3CURRENT");
        account3Current.setType(AccountType.CURRENT);
        account3Current.setActive(true);
        account3Current.setBalance(2000.00);
        account3Current.setAbsoluteLimit(-800.00);
        account3Current.setPincode(1234);
        account3Current.setCurrency(Currency.EURO);

        Account account3Savings = new Account();
        account3Savings.setUserid(4L);
        account3Savings.setIban("IBAN3SAVINGS");
        account3Savings.setType(AccountType.SAVINGS);
        account3Savings.setActive(true);
        account3Savings.setBalance(2000.00);
        account3Savings.setAbsoluteLimit(-800.00);
        account3Savings.setPincode(1234);
        account3Savings.setCurrency(Currency.EURO);

        Account bankAccount = new Account();
        bankAccount.setUserid(1L);
        bankAccount.setIban("NL01INHO0000000001");
        bankAccount.setType(AccountType.CURRENT);
        bankAccount.setActive(true);
        bankAccount.setBalance(1000000.00);
        bankAccount.setAbsoluteLimit(-1000000.00);
        bankAccount.setPincode(1234);
        bankAccount.setCurrency(Currency.EURO);

        List<Account> accounts =
                List.of(
                        account1Current, account1Savings, account2Current, account2Savings,account3Current,account3Savings, bankAccount
                );

        accounts.forEach(
                accountService::add
        );

        Transaction transaction1 = new Transaction();
        transaction1.setUserPerforming(1L);
        transaction1.setAccountFrom("IBAN1CURRENT");
        transaction1.accountTo("IBAN1SAVINGS");
        transaction1.amount(260.23);
        transaction1.executionDate(LocalDateTime.of(2022, 6, 11, 12, 0, 0));
        transaction1.setDescription("desc1");

        Transaction transaction2 = new Transaction();
        transaction2.userPerforming(2L);
        transaction2.setAccountFrom("IBAN2CURRENT");
        transaction2.accountTo("IBAN2SAVINGS");
        transaction2.amount(36.2);
        transaction2.executionDate(LocalDateTime.of(2022, 6, 11, 13, 15,20));
        transaction2.setDescription("rent");

        Transaction transaction3 = new Transaction();
        transaction3.setUserPerforming(1L);
        transaction3.setAccountFrom("IBAN1CURRENT");
        transaction3.accountTo("IBAN2CURRENT");
        transaction3.amount(888.0);
        transaction3.executionDate(LocalDateTime.of(2022, 6, 11, 12, 0, 0));
        transaction3.setDescription("gift");

        Transaction transaction4 = new Transaction();
        transaction4.setUserPerforming(1L);
        transaction4.setAccountFrom("IBAN2SAVINGS");
        transaction4.accountTo("IBAN1CURRENT");
        transaction4.amount(14.0);
        transaction4.executionDate(LocalDateTime.of(2022, 6, 11, 12, 0, 0));
        transaction4.setDescription("rent22");

        List<Transaction> transactions =
                List.of(
                        transaction1,
                        transaction2,
                        transaction3,
                        transaction4
                );

        transactionRepository.saveAll(transactions);
    }
}
