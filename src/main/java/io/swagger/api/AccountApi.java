package io.swagger.api;

import io.swagger.model.Account;

import java.math.BigDecimal;

import io.swagger.model.User;
import io.swagger.model.dto.*;
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
import javax.validation.constraints.NotNull;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")
@Validated
public interface AccountApi {

    @Operation(summary = "", description = "Check the total balance of the account", tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "balance for an account", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/account/{IBAN}/balance",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<BalanceDTO> getAccountBalance(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);


    @Operation(summary = "", description = "Retrieve the settings (limit) of the given account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "The account settings", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountSettingsDTO.class))),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/account/{IBAN}/settings",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<AccountSettingsDTO> getAccountSettings(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);


    @Operation(summary = "", description = "Edit the account settings", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Successfully changed the account settings"),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/account/{IBAN}/settings",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> accountIBANSettingsPut(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody AccountSettingsDTO body);


    @Operation(summary = "", description = "Check the total balance of the account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = ""),

        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),

        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),

        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/account/search",
        method = RequestMethod.GET)
    ResponseEntity<List<AccountSearchDTO>>  getAccountByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "lastName", required = true) String lastName, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "firstName", required = false) String firstName);


    @Operation(summary = "lock or unlock the account", description = "Activate or lock the given account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "202", description = "Successfully activated or closed the account"),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/account/{IBAN}/active",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<ActivateDTO> activateAccount(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "Activate or lock an account", required=true, schema=@Schema()) @Valid @RequestBody ActivateDTO body);


    @Operation(summary = "", description = "crate a new bank account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = ""),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user") })
    @RequestMapping(value = "/account",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Account> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "Account object to be created", required=true, schema=@Schema()) @Valid @RequestBody AccountDTO body);


    @Operation(summary = "", description = "Get list of all accounts", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A list of all accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user") })
    @RequestMapping(value = "/account",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset);

    @RequestMapping(value = "/account/employee",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccountsEmployee ();
}

