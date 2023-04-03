package io.swagger.model.enums;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AccountType {
    @JsonProperty("SAVINGS")
    SAVINGS,

    @JsonProperty("CURRENT")
    CURRENT;
}
