
package io.swagger.api;

import java.math.BigDecimal;
import io.swagger.model.User;
import io.swagger.model.dto.BalanceDTO;
import io.swagger.model.dto.LimitsDTO;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.dto.UserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")
@Validated
public interface UserApi {

    @Operation(summary = "", description = "Register a new user", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = ""),

        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),

        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user") })

    @RequestMapping(value = "/user",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<UserDTO> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody User body);


    @Operation(summary = "", description = "Get the total balance of all accounts for a user", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "total balance", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),

        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),

        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),

        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/user/{userId}/totalBalance",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<BalanceDTO> getTotalBalance(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Long userId);


    @Operation(summary = "", description = "Get a list of user(s), can be filtered", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "A list of all users", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),

        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),

        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user") })


    @RequestMapping(value = "/user",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<UserDTO>> getUsers(@Min(0)@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "offset", required = false) Integer offset);



    @RequestMapping(value = "/user/noAccount",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<UserDTO>> getUsersWithoutAccount(@Min(0)@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }
    )) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }, maximum="50"
    )) @Valid @RequestParam(value = "offset", required = false) Integer offset);


    @Operation(summary = "", description = "Update a user", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = ""),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),

            @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
            @ApiResponse(responseCode = "404", description = "Cannot find the value") })


    @RequestMapping(value = "/user",
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateUser (@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserUpdateDTO body);



    @Operation(summary = "", description = "Get the limits for a user", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "total balance", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),

            @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),

            @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/user/{userId}/limits",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<LimitsDTO> getLmits(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Long userId);


    @RequestMapping(value = "/user/employee",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<UserDTO> getUserEmployee ();


}

