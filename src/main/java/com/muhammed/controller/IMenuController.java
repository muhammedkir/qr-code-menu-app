package com.muhammed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.muhammed.dto.DtoCreateMenuRequest;
import com.muhammed.dto.DtoCreateMenuResponse;
import com.muhammed.dto.DtoMenu;

public interface IMenuController {

	public ResponseEntity<DtoCreateMenuResponse> createMenu(DtoCreateMenuRequest request);
	
	public List<DtoMenu> getMenusbyBranchId(Long branchId);
	
	public ResponseEntity<Void> addProduct(Long menuId, Long productId);
	
	public ResponseEntity<Void> removeProduct(Long menuId, Long productId);
	
	public ResponseEntity<Void> updateMenu(Long menuId, String name);
}
