package io.swagger.repository;

import io.swagger.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, String>  {

    @Query(value = "SELECT * FROM ACCOUNT WHERE IBAN != 'NL01INHO0000000001'", nativeQuery = true)
    List<Account> getAllAccounts();

    @Query(value = "SELECT * FROM ACCOUNT WHERE ACCOUNT.USERID = :id", nativeQuery = true)
    List<Account> getAllAccountsForUserId(Long id);

    @Query(value = "SELECT * FROM ACCOUNT JOIN `USER` ON ACCOUNT.USERID = `USER`.ID WHERE `USER`.USERNAME = :username", nativeQuery = true)
    List<Account> getAllAccountsForUsername(String username);

    @Query(value = "select balance from Account where iban = :iban AND iban !='NL01INHO0000000001'", nativeQuery = true)
    Double getBalance(@Param("iban") String iban);

    @Query("SELECT iban FROM Account ")
    List<String> getAllIban();

    @Query (value = "select * from Account where Account.iban = :iban", nativeQuery = true)
    Account getAccountSettingByIban(String iban);

    @Query(value = "SELECT * FROM Account AS A WHERE A.iban = :iban", nativeQuery = true)
    Account getAccountByIban (String iban);

    @Transactional
    @Modifying
    @Query(value = "update Account set Account.pincode = :pincode, absolute_Limit = :absoluteLimit where iban = :iban", nativeQuery = true)
    void updateSettings (String iban, Integer pincode, Double absoluteLimit);

    @Transactional
    @Modifying
    @Query(value = "update Account set Account.balance = Account.balance + :amount where Account.iban = :iban", nativeQuery = true)
    void updateBalance(String iban, Double amount);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Account set active = :active where iban = :iban", nativeQuery = true)
    void activate (String iban, Boolean active);

   // @Query(value = "SELECT * FROM ACCOUNT JOIN USER ON USER.ID = ACCOUNT.USERID WHERE USER.LASTNAME = :lastName OR USER.FIRSTNAME = :firstName", nativeQuery = true)
   // List<Account> getAccountByName (String lastName, String firstName);

     @Query(value = "SELECT * FROM ACCOUNT JOIN USER ON `USER`.ID = ACCOUNT.USERID WHERE `USER`.ID = :userId", nativeQuery = true)
     List<Account> getAccountByUserId (Long userId);

}
