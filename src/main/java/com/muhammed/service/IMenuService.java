package com.muhammed.service;

import java.util.List;

import com.muhammed.dto.DtoCreateMenuRequest;
import com.muhammed.dto.DtoCreateMenuResponse;
import com.muhammed.dto.DtoMenu;

public interface IMenuService {

	public DtoCreateMenuResponse createMenu(DtoCreateMenuRequest request);
	
	public List<DtoMenu> getMenusbyBranchId(Long branchId);
	
	public void addProductToMenu(Long menuId, Long productId);
	
	public void removeProductFromMenu(Long menuId, Long productId);
	
	public void updateMenuName(Long menuId, String newName);
}
