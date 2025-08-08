package com.muhammed.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoBranch;
import com.muhammed.dto.DtoBranchIU;
import com.muhammed.model.Branch;
import com.muhammed.repository.BranchRepository;
import com.muhammed.service.IBranchService;

@Service
public class BranchServiceImpl implements IBranchService {

	@Autowired
    private BranchRepository branchRepository;
	
	private String uploadDir = "uploads/branch-logos/";
	
	@Override
	public DtoBranch saveBranch(DtoBranchIU  dtoBranchIU) {
    	DtoBranch response = new DtoBranch();
    	Branch branch = new Branch();
    	BeanUtils.copyProperties(dtoBranchIU, branch);
    	
    	Branch dbBranch = branchRepository.save(branch);
    	BeanUtils.copyProperties(dbBranch, response);

    	return response;
    }

	@Override
	public List<DtoBranch> getAllBranchs() {
		List<Branch> branchList = branchRepository.findAll();
		List<DtoBranch> dtoList = new ArrayList<>();
		
		for (Branch branch : branchList) {
			DtoBranch dto = new DtoBranch();
			BeanUtils.copyProperties(branch, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public DtoBranch getBranchById(Long id) {
		Optional<Branch> optional =  branchRepository.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		Branch branch = optional.get();
		DtoBranch dtoBranch = new DtoBranch();
		
		BeanUtils.copyProperties(branch, dtoBranch);
		return dtoBranch;
	}

	@Override
	public void deleteBranch(Long id) {
		if(!branchRepository.existsById(id)) {
			
		}
		branchRepository.deleteById(id);
		
	}

	@Override
	public DtoBranch updateBranch(Long id, DtoBranch dtoBranch) {
		Optional<Branch> optional =  branchRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		Branch dbBranch =optional.get();
		BeanUtils.copyProperties(dtoBranch, dbBranch);
		
		Branch updated = branchRepository.save(dbBranch);
		
		DtoBranch response = new DtoBranch();
		
		BeanUtils.copyProperties(updated, response);
		return response;
	}

	
	@Override
	public Branch createBranch(String name, String address,String phoneNumber, String restaurantCode, MultipartFile logoFile ) {
	
		Branch branch = new Branch();
		branch.setName(name);
		branch.setAddress(address);
		branch.setPhoneNumber(phoneNumber);
		branch.setRestaurantCode(restaurantCode);
		
		if (logoFile != null && !logoFile.isEmpty()) {
	        String fileName = System.currentTimeMillis() + "_" + logoFile.getOriginalFilename();
	        Path filePath = Paths.get(uploadDir + File.separator + fileName);
	        try {
	            Files.copy(logoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	            branch.setLogoPath(fileName);
	        } catch (IOException e) {
	            e.printStackTrace(); // veya hata fırlatılabilir
	        }
	    }
		return branchRepository.save(branch);
	}

	@Override
	public List<DtoBranch> getBranchesByuserId(Long userId) {
	    List<Branch> branchList = branchRepository.findByUserId(userId);
	    List<DtoBranch> dtoList = new ArrayList<>();

	    for (Branch branch : branchList) {
	    	DtoBranch dto = new DtoBranch();
	        BeanUtils.copyProperties(branch, dto);
	       dto.setUserId(branch.getUserId());
	        dtoList.add(dto);
	    }

	    return dtoList;
	}
		
	@Override
	   public Optional<Branch> getBranchByRestaurantCode(String code) {
	       return branchRepository.findByRestaurantCode(code);
	    }	
			

	 @Override
	 public String uploadLogo(String name, String address, String phoneNumber, String restaurantCode, Long userId,
			MultipartFile logoFile) {
		 try {
	            // 1. Görseli yükle
	           	String uploadDir = "uploads/";
	           	String fileName = UUID.randomUUID() + "_" + logoFile.getOriginalFilename();
	            Path filePath = Paths.get(uploadDir, fileName);
	            Files.createDirectories(filePath.getParent());
	            Files.write(filePath, logoFile.getBytes());
	            

	            // 2. Branch oluştur
	            Branch branch = new Branch();
	            branch.setName(name);
	            branch.setAddress(address);
	            branch.setPhoneNumber(phoneNumber);
	            branch.setRestaurantCode(restaurantCode);
	            branch.setUserId(userId);
	            branch.setLogoPath(fileName); // null da olabilir

	            branchRepository.save(branch);
	            
	            return "Ürün başarıyla oluşturuldu";

	        } catch (IOException e) {
	            throw new RuntimeException("Logo yüklenirken hata oluştu: " + e.getMessage());
	        }	    
	 }
	    
}