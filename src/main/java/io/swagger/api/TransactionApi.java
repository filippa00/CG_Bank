package io.swagger.api;

import java.math.BigDecimal;
import io.swagger.model.Transaction;
import io.swagger.model.dto.AtmDTO;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")
@Validated
public interface TransactionApi {

    @Operation(summary = "", description = "Transfer money from one account to another", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "List of transactions", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/transaction",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> getTransactions(@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "ibanFrom", required = false) String ibanFrom, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "ibanTo", required = false) String ibanTo, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateFrom, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateTo, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "amount", required = false) Double amount, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "operator", required = false) Long operator, @Min(0)@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "offset", required = false) Integer offset);


    @Operation(summary = "", description = "Deposit money to account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Money deposited succesfully"),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/transaction/{IBAN}/deposit",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Transaction> deposit(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "ATM action", required=true, schema=@Schema()) @Valid @RequestBody AtmDTO body);


    @Operation(summary = "", description = "Withdraw money from the account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Money withdrawn succesfully"),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/transaction/{IBAN}/withdraw",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Transaction> withdraw(@Parameter(in = ParameterIn.PATH, description = "accountNumber", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "ATM action", required=true, schema=@Schema()) @Valid @RequestBody AtmDTO body);


    @Operation(summary = "", description = "Provides transactions information for a given account.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "List of transactions", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class)))),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/transaction/{IBAN}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<TransactionDTO>> getIBANTransactions(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateFrom, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateTo, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "IBANTo", required = false) String ibanTo, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "IBANFrom", required = false) String ibanFrom, @DecimalMin("0.01")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "amountEquals", required = false) BigDecimal amountEquals, @DecimalMin("0.01")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "amountBigger", required = false) BigDecimal amountBigger, @DecimalMin("0.01")@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "amountSmaller", required = false) BigDecimal amountSmaller, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset);


    @Operation(summary = "", description = "Transfer money from one account to another", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Succesful transfer"),
        
        @ApiResponse(responseCode = "400", description = "Bad request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid."),
        
        @ApiResponse(responseCode = "403", description = "Action is forbidden for this user"),
        
        @ApiResponse(responseCode = "404", description = "Cannot find the value") })
    @RequestMapping(value = "/transaction",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Transaction> transferTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody TransactionDTO body);

}

