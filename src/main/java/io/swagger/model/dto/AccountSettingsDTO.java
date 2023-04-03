package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Account IBAN by name
 */
@Schema(description = "Account IBAN by name")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")


public class AccountSettingsDTO {

    @JsonProperty("pincode")
    private Integer pincode = null;

    @JsonProperty("absoluteLimit")
    private Double absoluteLimit = null;

    public AccountSettingsDTO pincode(Integer pincode) {
        this.pincode = pincode;
        return this;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public AccountSettingsDTO absoluteLimit(Double absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
        return this;
    }

    public Double getAbsoluteLimit() {
        return absoluteLimit;
    }

    public void setAbsoluteLimit(Double absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountSettingsDTO accountSettingsDTO = (AccountSettingsDTO) o;
        return Objects.equals(this.pincode, accountSettingsDTO.pincode) &&
                Objects.equals(this.absoluteLimit, accountSettingsDTO.absoluteLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pincode, absoluteLimit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountSettingsDTO {\n");

        sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
        sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
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
