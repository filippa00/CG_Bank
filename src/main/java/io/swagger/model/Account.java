package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enums.AccountType;
import io.swagger.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.*;
/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")

@Entity
@Table(name = "\"account\"")
public class Account   {

  @Id
  @JsonProperty("iban")
  private String iban = null;

  @JsonProperty("userid")
//  @JoinColumn(name="id")
  private Long userid = null;

  @JsonProperty("type")
  private AccountType type = null;

  @JsonProperty("balance")
  private Double balance = null;

  @JsonProperty("currency")
  private Currency currency = null;

  @JsonProperty("active")
  private Boolean active = null;

  @JsonProperty("absoluteLimit")
  private Double absoluteLimit = null;

  @JsonProperty("pincode")
  private Integer pincode = null;

  public Account userid(Long userid) {
    this.userid = userid;
    return this;
  }

  /**
   * Get userid
   * @return userid
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Long getUserid() {
    return userid;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public Account iban(String iban) {
    this.iban = iban;
    return this;
  }

  /**
   * Get iban
   * @return iban
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(required = true, description = "")

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Account active (Boolean active) {
    this.active = active;
    return this;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Boolean getActive() {
    return active;
  }

  /**
   * Get locked
   * @return locked
   **/
  @Schema(example = "false", required = true, description = "")
      @NotNull

    public Boolean isActive() {
    return active;
  }

  public void setActive(Boolean locked) {
    this.active = locked;
  }

  public Account absoluteLimit(Double absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
    return this;
  }

  /**
   * Get absoluteLimit
   * @return absoluteLimit
   **/
  @Schema(example = "-500.01", required = true, description = "")

  public Double getAbsoluteLimit() {
    return absoluteLimit;
  }

  public void setAbsoluteLimit(Double absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
  }

  public Account pincode(Integer pincode) {
    this.pincode = pincode;
    return this;
  }

  /**
   * Get pincode
   * @return pincode
   **/
  @Schema(example = "1324", required = true, description = "")

  public Integer getPincode() {
    return pincode;
  }

  public void setPincode(Integer pincode) {
    this.pincode = pincode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.userid, account.userid) &&
        Objects.equals(this.iban, account.iban) &&
        Objects.equals(this.type, account.type) &&
        Objects.equals(this.balance, account.balance) &&
        Objects.equals(this.currency, account.currency) &&
        Objects.equals(this.active, account.active) &&
        Objects.equals(this.absoluteLimit, account.absoluteLimit) &&
        Objects.equals(this.pincode, account.pincode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userid, iban, type, balance, currency, active, absoluteLimit, pincode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    userid: ").append(toIndentedString(userid)).append("\n");
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    locked: ").append(toIndentedString(active)).append("\n");
    sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
