package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.LocalDateTime;

import java.util.Objects;

/**
 * AtmDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")


public class AtmDTO {
  @JsonProperty("executionDate")
  private LocalDateTime executionDate = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("pincode")
  private Integer pincode = null;

  public AtmDTO executionDate(LocalDateTime executionDate) {
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

  public AtmDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * minimum: 0
   * @return amount
   **/
  @Schema(description = "")

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  /**
   * Get accountTo
   * @return accountTo
   **/
  @Schema(example = "NL09INHO0123456", description = "")

  public AtmDTO pincode (Integer pincode) {
    this.pincode = pincode;
    return this;
  }

  /**
   * Get accountFrom
   * @return accountFrom
   **/
  @Schema(example = "NL09INHO0123456", description = "")
  
    public Integer getPincode() {
    return pincode;
  }

  public void setPincode (Integer pincode) {
    this.pincode = pincode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AtmDTO atmDTO = (AtmDTO) o;
    return Objects.equals(this.executionDate, atmDTO.executionDate) &&
        Objects.equals(this.amount, atmDTO.amount) &&
        Objects.equals(this.pincode, atmDTO.pincode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executionDate, amount, pincode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AtmDTO {\n");
    
    sb.append("    executionDate: ").append(toIndentedString(executionDate)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
