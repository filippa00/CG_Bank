package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


    @Query(value = "SELECT * FROM Transaction where account_From = :iban OR account_To = :iban ORDER BY execution_Date desc",nativeQuery = true)
    List<Transaction> getAllTransactionsForIban(String iban);

    @Query(value = "SELECT DISTINCT(TRANSACTION.ID), TRANSACTION.ACCOUNT_FROM, TRANSACTION.ACCOUNT_TO, TRANSACTION.AMOUNT, TRANSACTION. DESCRIPTION, TRANSACTION.EXECUTION_DATE, TRANSACTION.USER_PERFORMING FROM TRANSACTION JOIN ACCOUNT ON ACCOUNT.IBAN = TRANSACTION.ACCOUNT_FROM OR ACCOUNT.IBAN = TRANSACTION.ACCOUNT_TO JOIN `USER` ON `USER`.ID = ACCOUNT.USERID WHERE `USER`.ID = :userId",nativeQuery = true)
    List<Transaction> getAllTransactionsForUserId (Long userId);

    @Query(value = "SELECT * FROM Transaction WHERE TRANSACTION.ACCOUNT_FROM != 'NL01INHO0000000001' OR TRANSACTION.ACCOUNT_TO != 'NL01INHO0000000001'", nativeQuery = true)
    List<Transaction> getAllTransactions(String ibanFrom, String ibanTo, LocalDate dateFrom, LocalDate dateTo, Double amount, String operator);
}

