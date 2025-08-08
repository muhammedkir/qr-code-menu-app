package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.controller.IBranchController;
import com.muhammed.dto.DtoBranch;
import com.muhammed.dto.DtoBranchIU;
import com.muhammed.model.Branch;
import com.muhammed.service.IBranchService;

@RestController
@RequestMapping("/rest/api/branch")
@CrossOrigin(origins = "http://localhost:3000")
public class BranchControllerImpl implements IBranchController {

	@Autowired
	private IBranchService branchService;
	
	
	@PostMapping(path="/save")
	@Override
	public DtoBranch saveBranch(@RequestBody DtoBranchIU  dtoBranchIU) {
		return branchService.saveBranch(dtoBranchIU);
	}

	@GetMapping(path="/list")
	@Override
	public List<DtoBranch> getAllBranchs() {
		return branchService.getAllBranchs();
	}

	@GetMapping(path="/list/{id}")
	@Override
	public DtoBranch getBranchById(@PathVariable(name="id") Long id) {
		return branchService.getBranchById(id);
	}

	@DeleteMapping(path="/delete/{id}")
	@Override
	public void deleteBranch(@PathVariable Long id) {
		branchService.deleteBranch(id);
	}

	@PutMapping(path="/update/{id}")
	@Override
	public DtoBranch updateBranch(@PathVariable Long id,@RequestBody DtoBranch dtoBranch) {
		return branchService.updateBranch(id, dtoBranch);
	}
	
	@Override
	 @PostMapping("/create")
    public ResponseEntity<?> createBranch(
        @RequestParam String name,
        @RequestParam String address,
        @RequestParam String phone,
        @RequestParam String restaurantCode,
        @RequestParam(required = false) MultipartFile logoFile
    ) {
        try {
            Branch created = branchService.createBranch(name, address, phone, restaurantCode, logoFile);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Görsel yükleme hatası");
        }
    }

	@GetMapping(path="/user")
	@Override
	public ResponseEntity<List<DtoBranch>> getBranchesByUserId(@RequestParam Long userId) {
        List<DtoBranch> list = branchService.getBranchesByuserId(userId);
        return ResponseEntity.ok(list);
    }
	
	@Override
	@GetMapping(path="/by-code")
    public ResponseEntity<Branch> getBranchByCode(@RequestParam String code) {
        return branchService.getBranchByRestaurantCode(code)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
	
	@Override
	@PostMapping("/upload")
	public ResponseEntity<String> uploadLogo(
			@RequestParam String name,
	        @RequestParam String address,
	        @RequestParam String phoneNumber,
	        @RequestParam String restaurantCode,
	        @RequestParam Long userId, // userId parametresini ekleyin
	        @RequestParam("image") MultipartFile logoFile) {
	    
	    String response = branchService.uploadLogo(name, address, phoneNumber, restaurantCode, userId, logoFile);
	    return ResponseEntity.ok(response);   
	}

}
