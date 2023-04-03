
package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Request body type for login
 */
@Schema(description = "Request body type for login")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-15T11:00:41.830Z[GMT]")


public class ActivateDTO {
    @JsonProperty("activate")
    private Boolean activate = null;

    public ActivateDTO activate(Boolean activate) {
        this.activate = activate;
        return this;
    }

    /**
     * Username of the user
     *
     * @return username
     **/
    @Schema(example = "test", required = true, description = "Username of the user")
    @NotNull
    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivateDTO activateDTO = (ActivateDTO) o;
        return Objects.equals(this.activate, activateDTO.activate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ActivateDTO {\n");

        sb.append("    activate: ").append(toIndentedString(activate)).append("\n");
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


