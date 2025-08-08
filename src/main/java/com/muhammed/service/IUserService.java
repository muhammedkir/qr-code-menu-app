package com.muhammed.service;

import java.util.List;

import com.muhammed.dto.DtoUser;
import com.muhammed.dto.DtoUserBranchResponse;
import com.muhammed.dto.dtoLoginRequest;
import com.muhammed.dto.dtoRegisterRequest;
import com.muhammed.model.User;

public interface IUserService {

	public DtoUser saveUser(DtoUser dtoUser);
	
	public List<DtoUser> getAllUsers();
	
	public DtoUser getUserById(Long id);
	
	public void deleteUser(Long id);
	
	public DtoUser updateUser(Long id, DtoUser dtoUser);
	
	public User login(dtoLoginRequest request);
	
	public User registerUser(dtoRegisterRequest request);
    
    public DtoUserBranchResponse getUserWithBranches(Long userId);
		
}