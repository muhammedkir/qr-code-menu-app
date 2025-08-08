package com.muhammed.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoBranch;
import com.muhammed.dto.DtoBranchIU;
import com.muhammed.model.Branch;

public interface IBranchService {

	public DtoBranch saveBranch(DtoBranchIU  dtoBranchIU);
	
	public List<DtoBranch> getAllBranchs();
	
	public DtoBranch getBranchById(Long id);
	
	public void deleteBranch(Long id);
	
	public DtoBranch updateBranch(Long id, DtoBranch dtoBranch);
	
	public Branch createBranch(String name, String address, String phoneNumber,String restaurantCode,MultipartFile logoFile);
	
	public List<DtoBranch> getBranchesByuserId(Long userId);
    
    public Optional<Branch> getBranchByRestaurantCode(String code);
    
    public String uploadLogo(
    		String name,
    		String address,
    		String phoneNumber,
    		String restaurantCode,
    		Long userId,
    		MultipartFile logoFile);
}
