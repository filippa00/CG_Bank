package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "select sum(Account.balance) from Account where Account.userid = :id AND Account.userid != 1", nativeQuery = true)
    Double getTotalBalance(Long id);

    @Query(value = "SELECT * FROM `USER` WHERE USERNAME != 'bankOwner'", nativeQuery = true)
    List<User> getAllUsers();

    @Transactional
    @Modifying
    @Query("update User set username = ?2, password = ?3, dayLimit = ?4, transactionLimit = ?5 where username = ?1")
    void updateUser(String username, String newUsername, String password, Double dayLimit, Double transactionLimit);

    User findByUsername(String username);

    User findUserById(Long id);

    @Query(value = "SELECT * FROM `USER` WHERE NOT EXISTS (SELECT * FROM ACCOUNT WHERE ACCOUNT.USERID = `USER`.ID)", nativeQuery = true)
    List<User> getUsersWithoutAccount();

    @Query(value = "SELECT * FROM `USER` AS U JOIN Account ON Account.userid = U.id WHERE Account.iban = :iban", nativeQuery = true)
    User getUserByIban(String iban);

    @Query(value = "SELECT ISNULL(SUM(AMOUNT),0) FROM TRANSACTION JOIN ACCOUNT ON ACCOUNT.IBAN = TRANSACTION.ACCOUNT_FROM JOIN USER ON ACCOUNT.USERID = `USER`.ID WHERE `USER`.ID = :userid AND EXECUTION_DATE > :today", nativeQuery = true)
    Double getDailyUsed (Long userid, LocalDate today);


}
