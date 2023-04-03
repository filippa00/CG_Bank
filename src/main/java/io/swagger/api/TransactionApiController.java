package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.Transaction;
import io.swagger.model.dto.AtmDTO;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")
@RestController
@Api(tags = "Transaction")
public class TransactionApiController implements TransactionApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Transaction>> getTransactions(@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "ibanFrom", required = false) String ibanFrom, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "ibanTo", required = false) String ibanTo, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateFrom, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateTo, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "amount", required = false) Double amount, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "operator", required = false) Long operator, @Min(0) @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema(allowableValues = {}
    )) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema(allowableValues = {}, maximum = "50"
    )) @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.ok().body(transactionService.getAllTransactions(amount,dateFrom,dateTo,ibanTo,ibanFrom,operator));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Transaction> deposit(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "ATM action", required = true, schema = @Schema()) @Valid @RequestBody AtmDTO body) {
        return ResponseEntity.status(201).body(transactionService.deposit(body, IBAN));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Transaction> withdraw(@Parameter(in = ParameterIn.PATH, description = "accountNumber", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.DEFAULT, description = "ATM action", required = true, schema = @Schema()) @Valid @RequestBody AtmDTO body) {
        return ResponseEntity.status(201).body(transactionService.withdraw(body, IBAN));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TransactionDTO>> getIBANTransactions(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateFrom, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy'T'HH:mm:ss") LocalDateTime dateTo, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "IBANTo", required = false) String ibanTo, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "IBANFrom", required = false) String ibanFrom, @DecimalMin("0.01") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "amountEquals", required = false) BigDecimal amountEquals, @DecimalMin("0.01") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "amountBigger", required = false) BigDecimal amountBigger, @DecimalMin("0.01") @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "amountSmaller", required = false) BigDecimal amountSmaller, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset) {
        return ResponseEntity.ok().body(transactionService.getAllTransactionsForIban(IBAN,dateFrom,dateTo,ibanTo,ibanFrom,amountEquals,amountBigger,amountSmaller ));
        //return ResponseEntity.ok().body(transactionService.getAllTransactionsForIban(IBAN));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Transaction> transferTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody TransactionDTO body) {
        return ResponseEntity.status(201).body(transactionService.add(body));
    }

}
