package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.User;
import io.swagger.model.enums.Role;
import org.springframework.validation.annotation.Validated;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")

public class UserDTO {

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("username")
    private String username = null;

//    @JsonProperty("password")
//    private String password = null;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRole(Role role) {
        this.roles.add(role);
    }

    @JsonProperty("firstname")
    private String firstname = null;

    @JsonProperty("lastname")
    private String lastname = null;

    @JsonProperty("dayLimit")
    private Double dayLimit = null;

    @JsonProperty("transactionLimit")
    private Double transactionLimit = null;

    public UserDTO id(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public UserDTO username(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public UserDTO password(String password) {
//        this.password = password;
//        return this;
//    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public UserDTO firstname (String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public UserDTO lastname (String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserDTO dayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
        return this;
    }

    public Double getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
    }

    public UserDTO transactionLimit(Double transactionLimit) {
        this.transactionLimit = transactionLimit;
        return this;
    }

    public Double getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Double transactionLimit) {
        this.transactionLimit = transactionLimit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(this.id, userDTO.id) &&
                Objects.equals(this.username, userDTO.username) &&
                Objects.equals(this.roles, userDTO.roles) &&
//              Objects.equals(this.password, userDTO.password) &&
                Objects.equals(this.firstname, userDTO.firstname) &&
                Objects.equals(this.lastname, userDTO.lastname) &&
                Objects.equals(this.dayLimit, userDTO.dayLimit) &&
                Objects.equals(this.transactionLimit, userDTO.transactionLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username,roles, firstname, lastname, dayLimit, transactionLimit );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserDTO {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
//        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    role: ").append(toIndentedString(roles)).append("\n");
        sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
        sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
        sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
        sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
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
