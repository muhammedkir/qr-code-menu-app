package com.muhammed.service;

import java.util.List;

import com.muhammed.dto.DtoRestaurantTable;

public interface IRestaurantTableService {

	public DtoRestaurantTable saveRestaurantTable(DtoRestaurantTable dtoRestaurantTable);
	
	public List<DtoRestaurantTable> getAllRestaurantTables();
	
	public DtoRestaurantTable getRestaurantTableById(Long id);
	
	public void deleteRestaurantTable(Long id);
	
	public DtoRestaurantTable updateRestaurantTable(Long id, DtoRestaurantTable dtoRestaurantTable);
}

