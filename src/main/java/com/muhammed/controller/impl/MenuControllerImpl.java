package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muhammed.controller.IMenuController;
import com.muhammed.dto.DtoCreateMenuRequest;
import com.muhammed.dto.DtoCreateMenuResponse;
import com.muhammed.dto.DtoMenu;
import com.muhammed.service.IMenuService;

@RestController
@RequestMapping("/rest/api/menu")
public class MenuControllerImpl implements IMenuController{

	@Autowired
	private IMenuService menuService;
	
	@PostMapping(path="/create")
	@Override
	public ResponseEntity<DtoCreateMenuResponse> createMenu(@RequestBody DtoCreateMenuRequest request) {
		DtoCreateMenuResponse response = menuService.createMenu(request);
       return ResponseEntity.ok(response);
    }

	@GetMapping(path="/list")
	@Override
	public List<DtoMenu> getMenusbyBranchId(Long branchId) {
		return menuService.getMenusbyBranchId(branchId);
	}
	
	
	@Override
    @PutMapping(path="/{menuId}/addProduct")
    public ResponseEntity<Void> addProduct(@PathVariable Long menuId, @RequestParam Long productId) {
        menuService.addProductToMenu(menuId, productId);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping(path="/{menuId}/removeProduct/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long menuId, @PathVariable Long productId) {
        menuService.removeProductFromMenu(menuId, productId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping(path="/{menuId}")
    public ResponseEntity<Void> updateMenu(@PathVariable Long menuId, @RequestBody String name) {
        menuService.updateMenuName(menuId, name);
        return ResponseEntity.ok().build();
    }
}
