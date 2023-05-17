package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.User;
import io.swagger.model.dto.BalanceDTO;
import io.swagger.model.dto.LimitsDTO;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.dto.UserUpdateDTO;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-06T22:10:19.196Z[GMT]")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "Users")

public class UserApiController implements UserApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private final UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.userService = userService;
    }

    public ResponseEntity<UserDTO> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody User body) {
            return ResponseEntity.status(201).body(userService.createUser(body));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserUpdateDTO body) {
            userService.updateUser(body);
            return ResponseEntity.status(204).build();
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BalanceDTO> getTotalBalance(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Long userId) {
            return ResponseEntity.ok().body(userService.getTotalBalance(userId));
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> getUsers(@Min(0)@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "limit", required = false) Integer limit,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "offset", required = false) Integer offset) {

            return ResponseEntity.ok().body(userService.getAllUsers());
        }


    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<UserDTO>> getUsersWithoutAccount(@Min(0)@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }
    )) @Valid @RequestParam(value = "limit", required = false) Integer limit,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={  }, maximum="50"
    )) @Valid @RequestParam(value = "offset", required = false) Integer offset) {

        return ResponseEntity.ok().body(userService.getUsersWithoutAccount());
    }


    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LimitsDTO> getLmits(Long userId) {
        return ResponseEntity.ok().body(userService.getLimits(userId));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<UserDTO> getUserEmployee() {
        return ResponseEntity.ok().body(userService.getUserEmployee());
    }

}
