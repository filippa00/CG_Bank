
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


public class LoginResponseDTO   {
    @JsonProperty("token")
    private String token = null;

    @JsonProperty("employee")
    private Boolean employee = null;

    @JsonProperty("accnountOwner")
    private Boolean accnountOwner = null;

    public LoginResponseDTO token(String token) {
        this.token = token;
        return this;
    }

    public LoginResponseDTO employee(Boolean employee) {
        this.employee = employee;
        return this;
    }

    public LoginResponseDTO accnountOwner (Boolean accnountOwner) {
        this.accnountOwner = accnountOwner;
        return this;
    }


    /**
     * Get token
     * @return token
     **/
    @Schema(example = "SomeToken", description = "")

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    public Boolean getAccnountOwner() {
        return accnountOwner;
    }

    public void setAccnountOwner(Boolean accnountOwner) {
        this.accnountOwner = accnountOwner;
    }



    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) o;
        return Objects.equals(this.token, loginResponseDTO.token) &&
                Objects.equals(this.employee, loginResponseDTO.employee) &&
                Objects.equals(this.accnountOwner, loginResponseDTO.accnountOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, employee, accnountOwner);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LoginResponseDTO {\n");

        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("    employee: ").append(toIndentedString(employee)).append("\n");
        sb.append("    accnountOwner: ").append(toIndentedString(accnountOwner)).append("\n");
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