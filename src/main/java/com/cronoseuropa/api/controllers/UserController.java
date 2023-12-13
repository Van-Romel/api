package com.cronoseuropa.api.controllers;

import com.cronoseuropa.api.configs.CustomExceptionHandler;
import com.cronoseuropa.api.exceptions.CronosApiException;
import com.cronoseuropa.api.models.dtos.SaveUserRequestDTO;
import com.cronoseuropa.api.models.dtos.UserResponseDTO;
import com.cronoseuropa.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.cronoseuropa.api.utils.Util.sanitizeId;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(
                            schema = @Schema(implementation = SaveUserRequestDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"name\":\"van-romel\",\n" +
                                    "    \"surname\":\"neto\",\n" +
                                    "    \"birthDate\":\"1999-11-30\"\n" +
                                    "}"),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid date format. Please use 'yyyy-MM-dd'",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 01:02\",\n" +
                                    "    \"path\": \"/users\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"Invalid date format. Please use 'yyyy-MM-dd'\",\n" +
                                    "        \"internalCode\": \"1003\"\n" +
                                    "    }\n" +
                                    "}"))
                    })})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User DTO",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SaveUserRequestDTO.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"name\":\"van-romel\",\n" +
                            "    \"surname\":\"neto\",\n" +
                            "    \"birthDate\":\"1999-11-30\"\n" +
                            "}")))
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody SaveUserRequestDTO dto) {
        var createdUser = userService.create(dto);
        return ResponseEntity.status(201).body(createdUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = {@Content(
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(value = "[\n" +
                                    "    {\n" +
                                    "        \"name\": \"van-gasfasf\",\n" +
                                    "        \"surname\": \"afasdas\",\n" +
                                    "        \"birthDate\": \"1999-12-30\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "        \"name\": \"van-romel\",\n" +
                                    "        \"surname\": \"neto\",\n" +
                                    "        \"birthDate\": \"1999-11-30\"\n" +
                                    "    }\n" +
                                    "]")
                    )}
            )
    })
    @Operation(summary = "Find all users")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<UserResponseDTO>> findAllUsers() {
        var users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"name\":\"van-romel\",\n" +
                                    "    \"surname\":\"neto\",\n" +
                                    "    \"birthDate\":\"1999-11-30\"\n" +
                                    "}")
                    )}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 00:59\",\n" +
                                    "    \"path\": \"/users/10\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"The user was not found\",\n" +
                                    "        \"internalCode\": \"2001\"\n" +
                                    "    }\n" +
                                    "}"),
                            mediaType = "application/json")})
    })
    @Operation(summary = "Find one user by id")
    @GetMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> findOneUserById(@PathVariable("userId") String userId) {

        var user = userService.findOneById(sanitizeId(userId));

        return ResponseEntity.ok(user);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User fields altered",
                    content = {@Content(
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"name\":\"van-romel\",\n" +
                                    "    \"surname\":\"neto\",\n" +
                                    "    \"birthDate\":\"1999-11-30\"\n" +
                                    "}")
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid date format. Please use 'yyyy-MM-dd'",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 01:02\",\n" +
                                    "    \"path\": \"/users\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"Invalid date format. Please use 'yyyy-MM-dd'\",\n" +
                                    "        \"internalCode\": \"1003\"\n" +
                                    "    }\n" +
                                    "}"))
                    }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 00:59\",\n" +
                                    "    \"path\": \"/users/10\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"The user was not found\",\n" +
                                    "        \"internalCode\": \"2001\"\n" +
                                    "    }\n" +
                                    "}"),
                            mediaType = "application/json")})
    })
    @Operation(summary = "Alter user fields by id")
    @Parameters(value = {
            @Parameter(name = "name", description = "User name"),
            @Parameter(name = "surname", description = "User surname"),
            @Parameter(name = "birthDate", description = "User birth date"),
    })
    @PatchMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> alterUserFieldsById(@PathVariable("userId") String userId,
                                                               @RequestParam(name = "name", required = false) String name,
                                                               @RequestParam(name = "surname", required = false) String surname,
                                                               @RequestParam(name = "birthDate", required = false) String birthDate) {

        var user = userService.alterFieldsById(sanitizeId(userId), name,
                surname, birthDate);

        return ResponseEntity.ok(user);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User fields altered",
                    content = {@Content(
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"name\":\"van-romel\",\n" +
                                    "    \"surname\":\"neto\",\n" +
                                    "    \"birthDate\":\"1999-11-30\"\n" +
                                    "}")
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid date format. Please use 'yyyy-MM-dd'",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 01:02\",\n" +
                                    "    \"path\": \"/users\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"Invalid date format. Please use 'yyyy-MM-dd'\",\n" +
                                    "        \"internalCode\": \"1003\"\n" +
                                    "    }\n" +
                                    "}"))
                    }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 00:59\",\n" +
                                    "    \"path\": \"/users/10\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"The user was not found\",\n" +
                                    "        \"internalCode\": \"2001\"\n" +
                                    "    }\n" +
                                    "}"),
                            mediaType = "application/json")})
    })
    @Operation(summary = "Alter user by id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User DTO",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SaveUserRequestDTO.class),
                    examples = @ExampleObject(value = "{\n" +
                            "    \"name\":\"van-romel\",\n" +
                            "    \"surname\":\"neto\",\n" +
                            "    \"birthDate\":\"1999-11-30\"\n" +
                            "}")))
    @PutMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponseDTO> alterUser(@PathVariable("userId") String userId,
                                                     @RequestBody SaveUserRequestDTO dto) {
        var users = userService.alterById(sanitizeId(userId), dto);

        return ResponseEntity.ok(users);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(
                            schema = @Schema(implementation = CustomExceptionHandler.CustomError.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "    \"timestamp\": \"2023-12-13 00:59\",\n" +
                                    "    \"path\": \"/users/10\",\n" +
                                    "    \"error\": {\n" +
                                    "        \"description\": \"The user was not found\",\n" +
                                    "        \"internalCode\": \"2001\"\n" +
                                    "    }\n" +
                                    "}"),
                            mediaType = "application/json")})
    })
    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteById(sanitizeId(userId));

        return ResponseEntity.noContent().build();
    }
}
