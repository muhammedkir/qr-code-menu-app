package com.muhammed.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhammed.dto.DtoBranchIU;
import com.muhammed.dto.DtoUser;
import com.muhammed.dto.DtoUserBranchResponse;
import com.muhammed.dto.dtoLoginRequest;
import com.muhammed.dto.dtoRegisterRequest;
import com.muhammed.model.Branch;
import com.muhammed.model.User;
import com.muhammed.repository.BranchRepository;
import com.muhammed.repository.UserRepository;
import com.muhammed.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	
	@Override
	public DtoUser saveUser(DtoUser dtoUser) {
		User user = new User();
		BeanUtils.copyProperties(dtoUser, user);
		
		DtoUser response = new DtoUser();
		User dbUser = userRepository.save(user);
		BeanUtils.copyProperties(dbUser, response);
		return response;
	}

	@Override
	public List<DtoUser> getAllUsers() {
		List<User> userList = userRepository.findAll();
		List<DtoUser> dtoList = new ArrayList<>();
		
		for (User user : userList) {
			DtoUser dto = new DtoUser();
			BeanUtils.copyProperties(user, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public DtoUser getUserById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		User user = optional.get();
		DtoUser dtoUser = new DtoUser();
		
		BeanUtils.copyProperties(user, dtoUser);
		return dtoUser;
	}

	@Override
	public void deleteUser(Long id) {
		if(!userRepository.existsById(id)) {
			
		}
		userRepository.deleteById(id);
		
	}

	@Override
	public DtoUser updateUser(Long id, DtoUser dtoUser) {
		Optional<User> optional = userRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		User dbUser = optional.get();
		BeanUtils.copyProperties(dtoUser, dbUser);
		
		User updated = userRepository.save(dbUser);
		
		DtoUser response = new DtoUser();
		
		BeanUtils.copyProperties(updated, response);
		return response;
	}
	
	@Override
	public User registerUser(dtoRegisterRequest request) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        
        return userRepository.save(user);
    }
	
	@Override
	public User login(dtoLoginRequest request) {
	    String email = request.getEmail();
	    String password = request.getPassword();

	    Optional<User> userOpt = userRepository.findByEmail(email);
	    if (userOpt.isPresent()) {
	        User user = userOpt.get();
	        if (user.getPassword().equals(password)) {
	            return user;
	        }
	    }

	    return null; 
	}	
	    
	@Override
	   public DtoUserBranchResponse getUserWithBranches(Long userId) {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	            
	            List<Branch> branches = branchRepository.findByUserId(userId);
	            List<DtoBranchIU> branchDtos = new ArrayList<>();

	            for (Branch branch : branches) {
	            	DtoBranchIU dto = new DtoBranchIU();
	                dto.setName(branch.getName());
	                dto.setAddress(branch.getAddress());
	                dto.setPhoneNumber(branch.getPhoneNumber());
	                dto.setLogoPath(branch.getLogoPath());
	                branchDtos.add(dto);
	            }

	            DtoUserBranchResponse response = new DtoUserBranchResponse();
	            response.setUsername(user.getUsername());
	            response.setEmail(user.getEmail());
	            response.setBranches(branchDtos);

	            return response;
	        }
}
