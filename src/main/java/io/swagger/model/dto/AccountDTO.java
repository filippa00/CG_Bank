package io.swagger.model.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * Account IBAN by name
 */
@Schema(description = "Account IBAN by name")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")


public class AccountDTO {

    @JsonProperty("userid")
    private Long userid = null;

    @JsonProperty("type")
    private AccountType type = null;

    @JsonProperty("absoluteLimit")
    private Double absoluteLimit = null;

    public AccountDTO userid(Long userid) {
        this.userid = userid;
        return this;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountDTO absoluteLimit(Double absoluteLimit) {
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
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountDTO accountDTO = (AccountDTO) o;
        return Objects.equals(this.userid, accountDTO.userid) &&
                Objects.equals(this.type, accountDTO.type) &&
                Objects.equals(this.absoluteLimit, accountDTO.absoluteLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, type, absoluteLimit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountDTO {\n");

        sb.append("    userid: ").append(toIndentedString(userid)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
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
