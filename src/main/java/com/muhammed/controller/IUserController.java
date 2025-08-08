package com.muhammed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.muhammed.dto.DtoUser;
import com.muhammed.dto.DtoUserBranchResponse;
import com.muhammed.dto.dtoLoginRequest;
import com.muhammed.dto.dtoRegisterRequest;
import com.muhammed.model.User;

public interface IUserController {

	public DtoUser saveUser(DtoUser  dtoUser);
	
	public List<DtoUser> getAllUsers();
	
	public DtoUser getUserById(Long id);
	
	public void deleteUser(Long id);
	
	public DtoUser updateUser(Long id, DtoUser dtoUser);
	
	public  ResponseEntity<User> register(@RequestBody dtoRegisterRequest request);
	
	public ResponseEntity<User> login(dtoLoginRequest request);
    
    public ResponseEntity<DtoUserBranchResponse> getUserWithBranches(Long userId);
	
}
