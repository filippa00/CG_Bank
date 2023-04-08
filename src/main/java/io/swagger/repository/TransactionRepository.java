package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


    @Query(value = "SELECT * FROM \"transaction\" where account_From = :iban OR account_To = :iban ORDER BY execution_Date desc",nativeQuery = true)
    List<Transaction> getAllTransactionsForIban(String iban);

    @Query(value = "SELECT DISTINCT(\"transaction\".ID), \"transaction\".ACCOUNT_FROM, \"transaction\".ACCOUNT_TO, \"transaction\".AMOUNT, \"transaction\". DESCRIPTION, \"transaction\".EXECUTION_DATE, \"transaction\".USER_PERFORMING FROM \"transaction\" JOIN \"account\" ON \"account\".IBAN = \"transaction\".ACCOUNT_FROM OR \"account\".IBAN = \"transaction\".ACCOUNT_TO JOIN \"user\" ON \"user\".ID = \"account\".USERID WHERE \"user\".ID = :userId",nativeQuery = true)
    List<Transaction> getAllTransactionsForUserId (Long userId);

    @Query(value = "SELECT * FROM \"transaction\" WHERE \"transaction\".ACCOUNT_FROM != 'NL01INHO0000000001' OR \"transaction\".ACCOUNT_TO != 'NL01INHO0000000001'", nativeQuery = true)
    List<Transaction> getAllTransactions(String ibanFrom, String ibanTo, LocalDate dateFrom, LocalDate dateTo, Double amount, String operator);
}

