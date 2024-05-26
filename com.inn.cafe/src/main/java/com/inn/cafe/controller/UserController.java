package com.inn.cafe.controller;

import com.inn.cafe.model.User;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.wrapper.UserWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Create a new user", description = "Creates a new user and saves it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<User> createUser(
            @RequestBody(description = "Details of the new user", required = true) User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary = "Find user by email", description = "Returns the user matching the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/findByEmail")
    public ResponseEntity<User> findByEmail(
            @Parameter(description = "Email of the user", required = true) @RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserWrapper.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        List<UserWrapper> users = userRepository.getAllUser();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Update user status", description = "Updates the status of the user with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "ID of the user", required = true) @RequestParam Integer id,
            @Parameter(description = "New status", required = true) @RequestParam String status) {
        userRepository.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all admins", description = "Returns a list of all admin emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admins retrieved successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/admins")
    public ResponseEntity<List<String>> getAllAdmins() {
        List<String> admins = userRepository.getAllAdmin();
        return ResponseEntity.ok(admins);
    }
}
