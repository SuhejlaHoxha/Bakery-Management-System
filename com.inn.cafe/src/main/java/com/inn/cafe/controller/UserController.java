package com.inn.cafe.controller;

import com.inn.cafe.model.User;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Api(value = "User Controller", description = "REST API për menaxhimin e përdoruesve")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Krijo përdorues të ri", notes = "Krijon një përdorues të ri dhe e ruan në databazë")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(
            @ApiParam(value = "Detajet e përdoruesit të ri", required = true) @RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @ApiOperation(value = "Gjej përdoruesin me email", notes = "Kthen përdoruesin që përputhet me email-in e dhënë")
    @GetMapping("/findByEmail")
    public ResponseEntity<User> findByEmail(
            @ApiParam(value = "Email-i i përdoruesit", required = true) @RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Merr të gjithë përdoruesit", notes = "Kthen listën e të gjithë përdoruesve")
    @GetMapping("/all")
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        List<UserWrapper> users = userRepository.getAllUser();
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "Përditëso statusin e përdoruesit", notes = "Përditëson statusin e përdoruesit me ID-në e dhënë")
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(
            @ApiParam(value = "ID e përdoruesit", required = true) @RequestParam Integer id,
            @ApiParam(value = "Statusi i ri", required = true) @RequestParam String status) {
        userRepository.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Merr të gjithë adminët", notes = "Kthen listën e emaileve të të gjithë adminëve")
    @GetMapping("/admins")
    public ResponseEntity<List<String>> getAllAdmins() {
        List<String> admins = userRepository.getAllAdmin();
        return ResponseEntity.ok(admins);
    }
}
