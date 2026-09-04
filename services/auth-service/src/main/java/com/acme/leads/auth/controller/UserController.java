package com.acme.leads.auth.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.leads.auth.model.User;
import com.acme.leads.auth.service.UserService;
import com.acme.leads.shared.controller.BaseController;
import com.acme.leads.shared.dto.UserDTO;
import com.acme.leads.shared.dto.UserDetailsDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserDetailsDTO, Long> {
    
    private @Autowired UserService service;

    @GetMapping("/{id}/public")
    public ResponseEntity<List<UserDTO>> getPublic(@PathVariable Set<Long> id) {
        return new ResponseEntity<>(service.findByIdPublic(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/username/{username}/id")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.findIdByUsername(username), HttpStatus.OK);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<UserDetailsDTO> patch(
            @PathVariable Long id, @RequestBody UserDetailsDTO DTO) {
        DTO.setId(id);
        return new ResponseEntity<>(this.service.update(DTO), HttpStatus.OK);
    }
}