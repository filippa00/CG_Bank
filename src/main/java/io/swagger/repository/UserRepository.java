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


    @Query(value = "select sum(account.balance) from \"account\" where \"account\".userid = :id AND \"account\".userid != 1", nativeQuery = true)
    Double getTotalBalance(Long id);

    @Query(value = "SELECT * FROM \"user\" WHERE USERNAME != 'bankOwner'", nativeQuery = true)
    List<User> getAllUsers();

    @Transactional
    @Modifying
    @Query("update User set username = ?2, password = ?3, dayLimit = ?4, transactionLimit = ?5 where username = ?1")
    void updateUser(String username, String newUsername, String password, Double dayLimit, Double transactionLimit);

    User findByUsername(String username);

    User findUserById(Long id);

    @Query(value = "SELECT * FROM \"user\" WHERE NOT EXISTS (SELECT * FROM \"account\" WHERE \"account\".USERID = \"user\".ID)", nativeQuery = true)
    List<User> getUsersWithoutAccount();

    @Query(value = "SELECT * FROM \"user\" AS U JOIN \"account\" ON \"account\".userid = U.id WHERE \"account\".iban = :iban", nativeQuery = true)
    User getUserByIban(String iban);

    @Query(value = "SELECT coalesce(SUM(AMOUNT),0) FROM \"transaction\" JOIN \"account\" ON \"account\".IBAN = \"transaction\".ACCOUNT_FROM JOIN \"user\" ON \"account\".USERID =  \"user\".ID WHERE  \"user\".ID = :userid AND EXECUTION_DATE > :today", nativeQuery = true)
    Double getDailyUsed (Long userid, LocalDate today);


}
