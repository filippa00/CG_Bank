
package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Request body type for login response
 */
@Schema(description = "Request body type for login response")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-15T11:00:41.830Z[GMT]")


public class LimitsDTO {

    @JsonProperty("dayLimit")
    private Double dayLimit = null;

    @JsonProperty("transactionLimit")
    private Double transactionLimit = null;

    @JsonProperty("dailyRemaining")
    private Double dailyRemaining = null;


    public LimitsDTO dayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
        return this;
    }

    public Double getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
    }

    public LimitsDTO transactionLimit(Double transactionLimit) {
        this.dayLimit = transactionLimit;
        return this;
    }

    public Double getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Double transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public LimitsDTO dailyRemaining(Double dailyRemaining) {
        this.dayLimit = dailyRemaining;
        return this;
    }

    public Double getDailyRemaining() {
        return dailyRemaining;
    }

    public void setDailyRemaining(Double dailyRemaining) {
        this.dailyRemaining = dailyRemaining;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LimitsDTO balanceDTO = (LimitsDTO) o;
        return Objects.equals(this.dayLimit, balanceDTO.dayLimit) &&
                Objects.equals(this.transactionLimit, balanceDTO.transactionLimit) &&
                Objects.equals(this.dailyRemaining, balanceDTO.dailyRemaining);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayLimit, transactionLimit, dailyRemaining);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LimitsDTO {\n");

        sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
        sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
        sb.append("    dailyRemaining: ").append(toIndentedString(dailyRemaining)).append("\n");
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