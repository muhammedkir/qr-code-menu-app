package com.muhammed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoBranch;
import com.muhammed.dto.DtoBranchIU;
import com.muhammed.model.Branch;

public interface IBranchController {

	public DtoBranch saveBranch(DtoBranchIU  dtoBranchIU);
	
	public List<DtoBranch> getAllBranchs();
	
	public DtoBranch getBranchById(Long id);
	
	public void deleteBranch(Long id);
	
	public DtoBranch updateBranch(Long id, DtoBranch dtoBranch);
	
	public ResponseEntity<?> createBranch(String name, String address, String phoneNumber, String restaurantCode, MultipartFile logoFile);
	
	public ResponseEntity<List<DtoBranch>> getBranchesByUserId(Long userId);
	
	public ResponseEntity<Branch> getBranchByCode( String code);
	
	public ResponseEntity<String> uploadLogo(
			String name,
			String address,
			String phoneNumber,
			String restaurantCode,
			Long userId,
			MultipartFile logoFile);

}
