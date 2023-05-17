package io.swagger.service;


import io.swagger.model.Account;
import io.swagger.model.dto.IbanTransactionRequestDTO;
import io.swagger.model.dto.TransactionRequestDTO;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.dto.AtmDTO;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.model.enums.AccountType;
import io.swagger.model.enums.Role;
import io.swagger.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionFilterRepository transactionFilterRepository;

    @Autowired
    IBANFilterRepository ibanFilterRepository;


    // As a customer, view all my transactions from May 2022 and then from June 2022
    public List<Transaction> getAllTransactions(Double amount, LocalDateTime dateFrom, LocalDateTime dateTo, String ibanTo, String ibanFrom, Long operator) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER) ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be an customer to see all transactions");
        }

        if(ibanTo != null)
        {
            if (ibanTo.equals("NL01INHO0000000001")) {
                throw new IllegalArgumentException("not allowed to access the bank's account");
            }
        }else if(ibanFrom != null)
        {
            if (ibanFrom.equals("NL01INHO0000000001")) {
                throw new IllegalArgumentException("not allowed to access the bank's account");
            }
        }
        List<Transaction>filteredTransactions = new ArrayList<>();

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setAmount(amount);
        transactionRequestDTO.setDateFrom(dateFrom);
        transactionRequestDTO.setDateTo(dateTo);
        transactionRequestDTO.setIbanTo(ibanTo);
        transactionRequestDTO.setIbanFrom(ibanFrom);
        transactionRequestDTO.setOperator(operator);

       List<Transaction> transactions = transactionFilterRepository.getTransactionByRequest(transactionRequestDTO);
       List<Transaction> usersTransactions = transactionRepository.getAllTransactionsForUserId(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());

       for(Transaction t : transactions){
           if(usersTransactions.contains(t))
           {
               filteredTransactions.add(t);
           };
       }
        return filteredTransactions;
    }

    public Transaction add(TransactionDTO dto) {

        validateAccount(dto.getAccountFrom());
        validateAccount(dto.getAccountTo());

        Account accountTo = accountRepository.getAccountByIban(dto.getAccountTo());
        Account accountFrom = accountRepository.getAccountByIban(dto.getAccountFrom());

        if (accountTo == accountFrom) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant transfer to and from the same account");
        }

        if (dto.getAmount() < 0.01) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be a positive number");
        }

        User accountOwnerFrom = userRepository.getUserByIban(accountFrom.getIban());

        // 5. As a bank, I want to prevent transfers from a savings account to an account that is not of the same customer.
        if (accountFrom.getType().equals(AccountType.SAVINGS) && accountFrom.getUserid() != accountTo.getUserid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant transfer from savings to another owner");
        }

        // 6.	As a bank, I want to prevent transfers to a savings account to an account that is not of the same customer.
        if (accountTo.getType().equals(AccountType.SAVINGS) && accountFrom.getUserid() != accountTo.getUserid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant transfer to savings from another owner");
        }

        // 9 As a customer, I want to be able to perform transactions
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            if (!accountOwnerFrom.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User can only transfer money from his own account");
            }
        }

        // 13 As a bank, I want to prevent an account balance becoming lower than a certain number, referred to as absolute limit.
        if ((accountFrom.getBalance() - dto.getAmount()) < accountFrom.getAbsoluteLimit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The balance will be too low");
        }

        if (accountOwnerFrom.getTransactionLimit() < dto.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The transaction amount is bigger than transaction limit");
        }

        //-	The cumulative value of transactions occurring on a day cannot surpass a certain number defined per user, referred to as day limit

        double amountLeft = accountOwnerFrom.getDayLimit() - userRepository.getDailyUsed(accountOwnerFrom.getId(), LocalDate.now());
        if (amountLeft < dto.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "With this transaction you will exceed your daily limit");
        }

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(dto.getAccountFrom());
        transaction.setAccountTo(dto.getAccountTo());
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setExecutionDate(LocalDateTime.now());
        transaction.setUserPerforming(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());

        // update the balance of the accounts
        accountRepository.updateBalance(accountFrom.getIban(), (-1 * dto.getAmount()));
        accountRepository.updateBalance(accountTo.getIban(), dto.getAmount());

        return transactionRepository.save(transaction);
    }

    public Transaction deposit(AtmDTO deposit, String iban) {

        atmValidation(deposit, iban);

        Transaction transaction = new Transaction();
        transaction.setAccountTo(iban);
        transaction.setAccountFrom("DEPOSIT");
        transaction.setDescription("ATM");
        transaction.setAmount(deposit.getAmount());
        transaction.setExecutionDate(LocalDateTime.now());
        transaction.setUserPerforming(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());

        accountRepository.updateBalance(iban, (transaction.getAmount()));
        return transactionRepository.save(transaction);

    }

    public Transaction withdraw(AtmDTO withdraw, String iban) {

        atmValidation(withdraw, iban);

        User accountOwner = userRepository.getUserByIban(iban);
        double amountLeft = accountOwner.getDayLimit() - userRepository.getDailyUsed(accountOwner.getId(), LocalDate.now());
        if (amountLeft < withdraw.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "With this withdrawal you will exceed your daily limit");
        }

        Account account = accountRepository.getAccountByIban(iban);
        if (((account.getBalance() - withdraw.getAmount()) < account.getAbsoluteLimit())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The balance will be too low");
        }

        if (accountOwner.getTransactionLimit() < withdraw.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The withdrawal amount is bigger than transaction limit");
        }

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(iban);
        transaction.setAccountTo("WITHDRAWAL");
        transaction.setDescription("ATM");
        transaction.setAmount(withdraw.getAmount());
        transaction.setExecutionDate(LocalDateTime.now());
        transaction.setUserPerforming(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());

        accountRepository.updateBalance(iban, (-1 * transaction.getAmount()));
        return transactionRepository.save(transaction);

    }

    private void validateAccount(String iban) {

        if (iban.equals("NL01INHO0000000001")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not allowed to access this account (bank)");
        }

        if (accountRepository.getAccountByIban(iban) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cant find this account");
        }

        if (!accountRepository.getAccountByIban(iban).getActive()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account is locked");
        }
    }

    private void atmValidation(AtmDTO atmDTO, String iban) {

        validateAccount(iban);

        User accountOwner = userRepository.getUserByIban(iban);
        User performing = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ROLE_CUSTOMER)) {
            if (accountOwner != performing) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Customer can only use atm for his own accounts");
            }
        }

        if (!accountRepository.getAccountByIban(iban).getPincode().equals(atmDTO.getPincode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong pincode");

        }

        if (atmDTO.getAmount() < 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ATM minimum amount is 5 euro");
        }
    }

    public List<TransactionDTO> getAllTransactionsForIban(String iban, LocalDateTime dateFrom, LocalDateTime dateTo, String ibanTo, String ibanFrom, BigDecimal amountEquals,BigDecimal amountBigger,BigDecimal amountSmaller) {


        if (accountRepository.getAccountByIban(iban) == null) {
            throw new IllegalArgumentException("account doesnt exist");
        }

//        if (accountRepository.getAllAccountsForUsername(SecurityContextHolder.getContext().getAuthentication().getName()) == null)
//        {
//            throw new IllegalArgumentException("you do not have an account please create one");
//        }else{
            List<Account>accounts = accountRepository.getAllAccountsForUserId(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
            if(!isUserAccount(accounts, iban))
            {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"this is not your account");
            }
//        }

        if (iban.equals("NL01INHO0000000001")) {
            throw new IllegalArgumentException("not allowed to access the bank's account");
        }
        IbanTransactionRequestDTO request = new IbanTransactionRequestDTO();

        List<Transaction> result = null;
        if (ibanTo == null && dateFrom == null && dateTo == null && amountBigger == null && amountSmaller == null && amountEquals == null){
            result = transactionRepository.getAllTransactionsForIban(iban);
        }else{
            request.setIbanTo(ibanTo);
            request.setIbanFrom(ibanFrom);
            request.setDateFrom(dateFrom);
            request.setDateTo(dateTo);
            request.setAmountBigger(amountBigger);
            request.setAmountSmaller(amountSmaller);
            request.setAmountEquals(amountEquals);
            result = ibanFilterRepository.GetIBANTransactionsByRequest(request,iban);
        }

        List<TransactionDTO> dtos = new ArrayList<>();

        for (var r : result) {
            TransactionDTO dto = new TransactionDTO();
            dto.setExecutionDate(r.getExecutionDate());
            dto.setAccountFrom(r.getAccountFrom());
            dto.setAccountTo(r.getAccountTo());
            dto.setAmount(r.getAmount());
            dtos.add(dto);
        }
        return dtos;
    }

    private boolean isUserAccount(List<Account>accounts, String iban){
        for(Account account : accounts){
            if(account.getIban().equals(iban)){
                return true;
            }
        }
        return false;
    }

}
