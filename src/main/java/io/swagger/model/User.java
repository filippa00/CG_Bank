package io.swagger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
//            (strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( columnDefinition = "serial")
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("username")
    private String username = null;

    @JsonProperty("password")
    private String password = null;

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

    @NotNull
    @JsonProperty("lastname")
    private String lastname = null;

    @JsonProperty("dayLimit")
    private Double dayLimit = null;

    @JsonProperty("transactionLimit")
    private Double transactionLimit = null;

    public User id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @Schema(example = "5261", description = "")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get username
     *
     * @return username
     **/
    @Schema(example = "Username", description = "")

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     *
     * @return password
     **/
    @Schema(example = "pa$$owrd", description = "")

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User role(List<Role> role) {
        this.roles = role;
        return this;
    }

    public void setRoles(List<Role> role) {
        this.roles = role;
    }

    public User firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * Get firstname
     *
     * @return firstname
     **/
    @Schema(example = "John", description = "")

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public User lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * Get lastname
     *
     * @return lastname
     **/
    @Schema(example = "Doe", description = "")

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public User dayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
        return this;
    }

    /**
     * Get dayLimit
     *
     * @return dayLimit
     **/
    @Schema(example = "2000", required = true, description = "")

    public Double getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Double dayLimit) {
        this.dayLimit = dayLimit;
    }

    public User transactionLimit(Double transactionLimit) {
        this.transactionLimit = transactionLimit;
        return this;
    }

    /**
     * Get transactionLimit
     *
     * @return transactionLimit
     **/
    @Schema(example = "950", required = true, description = "")

    public Double getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Double transactionLimit) {
        this.transactionLimit = transactionLimit;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.username, user.username) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.roles, user.roles) &&
                Objects.equals(this.firstname, user.firstname) &&
                Objects.equals(this.lastname, user.lastname) &&
                Objects.equals(this.dayLimit, user.dayLimit) &&
                Objects.equals(this.transactionLimit, user.transactionLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, firstname, lastname, dayLimit, transactionLimit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
