package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhammed.controller.IRestaurantTableController;
import com.muhammed.dto.DtoRestaurantTable;
import com.muhammed.service.IRestaurantTableService;

@RestController
@RequestMapping("/rest/api/table")
public class RestaurantTableControllerImpl implements IRestaurantTableController{

	@Autowired
	private IRestaurantTableService restaurantTableService;
	
	@PostMapping(path="/save")
	@Override
	public DtoRestaurantTable saveRestaurantTable(DtoRestaurantTable dtoRestaurantTable) {
		return restaurantTableService.saveRestaurantTable(dtoRestaurantTable);
	}

	@GetMapping(path="/list")
	@Override
	public List<DtoRestaurantTable> getAllRestaurantTables() {
		return restaurantTableService.getAllRestaurantTables();
	}

	@GetMapping(path="/list/{id}")
	@Override
	public DtoRestaurantTable getRestaurantTableById(Long id) {
		return restaurantTableService.getRestaurantTableById(id);
	}

	@DeleteMapping(path="/delete/{id}")
	@Override
	public void deleteRestaurantTable(Long id) {
		restaurantTableService.deleteRestaurantTable(id);		
	}

	@PutMapping(path="/update/{id}")
	@Override
	public DtoRestaurantTable updateRestaurantTable(Long id, DtoRestaurantTable dtoRestaurantTable) {
		return restaurantTableService.updateRestaurantTable(id, dtoRestaurantTable);
	}

}
