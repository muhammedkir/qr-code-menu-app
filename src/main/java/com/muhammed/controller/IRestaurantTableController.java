package com.muhammed.controller;

import java.util.List;

import com.muhammed.dto.DtoRestaurantTable;

public interface IRestaurantTableController {

	public DtoRestaurantTable saveRestaurantTable(DtoRestaurantTable dtoRestaurantTable);
	
	public List<DtoRestaurantTable> getAllRestaurantTables();
	
	public DtoRestaurantTable getRestaurantTableById(Long id);
	
	public void deleteRestaurantTable(Long id);
	
	public DtoRestaurantTable updateRestaurantTable(Long id, DtoRestaurantTable dtoRestaurantTable);
}
