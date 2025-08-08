package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhammed.controller.IUserController;
import com.muhammed.dto.DtoUser;
import com.muhammed.dto.DtoUserBranchResponse;
import com.muhammed.dto.dtoLoginRequest;
import com.muhammed.dto.dtoRegisterRequest;
import com.muhammed.model.User;
import com.muhammed.service.IUserService;

@RestController
@RequestMapping("/rest/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserControllerImpl implements IUserController{

	@Autowired
	private IUserService userService;
	
	@PostMapping(path="/save")
	@Override
	public DtoUser saveUser(@RequestBody DtoUser dtoUser) {
		return userService.saveUser(dtoUser);
	}

	@GetMapping(path="/list")
	@Override
	public List<DtoUser> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping(path="/list/{id}")
	@Override
	public DtoUser getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@DeleteMapping(path="/delete/{id}")
	@Override
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	@PutMapping(path="/update/{id}")
	@Override
	public DtoUser updateUser(@PathVariable Long id,@RequestBody DtoUser dtoUser) {
		return userService.updateUser(id, dtoUser);
	}
	
	@Override
	@PostMapping(path="/register")
    public  ResponseEntity<User> register(@RequestBody dtoRegisterRequest request) {
        User user = userService.registerUser(request);
        
        return ResponseEntity.ok(user);
    }
	
	@PostMapping(path="/login")
	@Override
	public ResponseEntity<User> login(@RequestBody dtoLoginRequest request) {
		User user = userService.login(request);
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}
	
    @Override
    @GetMapping(path="/info/{userId}")
    public ResponseEntity<DtoUserBranchResponse> getUserWithBranches(@PathVariable Long userId) {
    	DtoUserBranchResponse dto = userService.getUserWithBranches(userId);
        return ResponseEntity.ok(dto);
    }
	
}
