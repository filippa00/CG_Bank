package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Account IBAN by name
 */
@Schema(description = "Account IBAN by name")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")


public class AccountSearchDTO {



    @JsonProperty("type")
    private AccountType type = null;

    @JsonProperty("iban")
    private String iban = null;


    public void setIban(String iban) {
        this.iban = iban;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountSearchDTO iban(String iban) {
        this.iban = iban;
        return this;
    }

    public AccountSearchDTO type(AccountType type) {
        this.type = type;
        return this;
    }

    public String getIban() {
        return iban;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountSearchDTO accountDTO = (AccountSearchDTO) o;
        return Objects.equals(this.type, accountDTO.type) &&
                Objects.equals(this.iban, accountDTO.iban) ;

    }

    @Override
    public int hashCode() {
        return Objects.hash( type, iban);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountSearchDTO {\n");

        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
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
