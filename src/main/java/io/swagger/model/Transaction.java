package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")

@Entity
public class Transaction   {
  @Id
  @GeneratedValue
//          (strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("executionDate")
  @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss")
  private LocalDateTime executionDate = null;

  @JsonProperty("userPerforming")
  @JoinColumn(name="USER_PERFORMING")
  private Long userPerforming = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("accountTo")
  @JoinColumn(name="ACCOUNT_TO")
  private String accountTo = null;

  @JsonProperty("accountFrom")
  @JoinColumn(name="ACCOUNT_FROM")
  private String accountFrom = null;

  @JsonProperty("description")
  private String description = null;

  public Transaction id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "uti-135413-nknf", description = "")
  
    public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Transaction executionDate(LocalDateTime executionDate) {
    this.executionDate = executionDate;
    return this;
  }

  /**
   * Get executionDate
   * @return executionDate
   **/
  @Schema(description = "")

  public LocalDateTime getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(LocalDateTime executionDate) {
    this.executionDate = executionDate;
  }

  public Transaction userPerforming(Long userPerforming) {
    this.userPerforming = userPerforming;
    return this;
  }

  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(example = "6521", description = "")
  
    public Long getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(Long userPerforming) {
    this.userPerforming = userPerforming;
  }

  public Transaction amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * minimum: 0
   * @return amount
   **/
  @Schema(description = "")
  
    @Valid
  @DecimalMin("0")  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Transaction accountTo(String accountTo) {
    this.accountTo = accountTo;
    return this;
  }

  /**
   * Get accountTo
   * @return accountTo
   **/
  @Schema(example = "iban", description = "")
  
    public String getAccountTo() {
    return accountTo;
  }

  public void setAccountTo(String accountTo) {
    this.accountTo = accountTo;
  }

  public Transaction accountFrom(String accountFrom) {
    this.accountFrom = accountFrom;
    return this;
  }
  
    public String getAccountFrom() {
    return accountFrom;
  }

  public void setAccountFrom(String accountFrom) {
    this.accountFrom = accountFrom;
  }

  public Transaction description (String description) {
    this.description = description;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.executionDate, transaction.executionDate) &&
        Objects.equals(this.userPerforming, transaction.userPerforming) &&
        Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.accountTo, transaction.accountTo) &&
        Objects.equals(this.accountFrom, transaction.accountFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, executionDate, userPerforming, amount, accountTo, accountFrom);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    executionDate: ").append(toIndentedString(executionDate)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
    sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
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
