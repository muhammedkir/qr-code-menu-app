package com.muhammed.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhammed.dto.DtoCreateMenuRequest;
import com.muhammed.dto.DtoCreateMenuResponse;
import com.muhammed.dto.DtoMenu;
import com.muhammed.model.Menu;
import com.muhammed.repository.MenuRepository;
import com.muhammed.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService{

	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public DtoCreateMenuResponse createMenu(DtoCreateMenuRequest request) {

	    Menu menu = new Menu();
	    menu.setBranchId(request.getBranchId()); // ← düzeltildi
	    menu.setName(request.getName());
	    menu.setProductId(request.getProductIds());
	    
	    Menu savedMenu = menuRepository.save(menu);
	    
	    DtoCreateMenuResponse response = new DtoCreateMenuResponse();
	    BeanUtils.copyProperties(savedMenu, response);

	    return response;
	}


	@Override
	public List<DtoMenu> getMenusbyBranchId(Long branchId) {
	    List<Menu> menuList = menuRepository.findByBranchId(branchId);
	    List<DtoMenu> dtoList = new ArrayList<>();
	    
	    for (Menu menu : menuList) {
			DtoMenu dto = new DtoMenu();
			BeanUtils.copyProperties(menu, dto);
			dto.setBranchId(menu.getBranchId());
			dtoList.add(dto);
		}

	    return dtoList;
	}
	
	@Override
    public void addProductToMenu(Long menuId, Long productId) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("Menü bulunamadı"));

        List<Long> products = menu.getProductId();
        if (!products.contains(productId)) {
            products.add(productId);
            menu.setProductId(products);
            menuRepository.save(menu);
        }
    }

    @Override
    public void removeProductFromMenu(Long menuId, Long productId) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("Menü bulunamadı"));

        List<Long> products = menu.getProductId();
        products.removeIf(id -> id.equals(productId));
        menu.setProductId(products);
        menuRepository.save(menu);
    }

    @Override
    public void updateMenuName(Long menuId, String newName) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("Menü bulunamadı"));

        menu.setName(newName);
        menuRepository.save(menu);
    }

}
