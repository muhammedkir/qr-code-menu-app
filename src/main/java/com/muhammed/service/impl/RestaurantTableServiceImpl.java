package com.muhammed.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhammed.dto.DtoRestaurantTable;
import com.muhammed.model.RestaurantTable;
import com.muhammed.repository.RestaurantTableRepository;
import com.muhammed.service.IRestaurantTableService;

@Service
public class RestaurantTableServiceImpl implements IRestaurantTableService{

	@Autowired
	private RestaurantTableRepository restaurantTableRepository;
	
	@Override
	public DtoRestaurantTable saveRestaurantTable(DtoRestaurantTable dtoRestaurantTable) {
		RestaurantTable restaurantTable = new RestaurantTable();
		BeanUtils.copyProperties(dtoRestaurantTable, restaurantTable);
		
		DtoRestaurantTable response = new DtoRestaurantTable();
		RestaurantTable dbRestaurantTable = restaurantTableRepository.save(restaurantTable);
		BeanUtils.copyProperties(dbRestaurantTable, response);
		return response;
	}

	@Override
	public List<DtoRestaurantTable> getAllRestaurantTables() {
		List<RestaurantTable> restaurantTableList = restaurantTableRepository.findAll();
		List<DtoRestaurantTable> dtoList = new ArrayList<>();
		
		for (RestaurantTable restaurantTable : restaurantTableList) {
			DtoRestaurantTable dto = new DtoRestaurantTable();
			BeanUtils.copyProperties(restaurantTable, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public DtoRestaurantTable getRestaurantTableById(Long id) {
		Optional<RestaurantTable> optional = restaurantTableRepository.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		RestaurantTable restaurantTable = optional.get();
		DtoRestaurantTable dtoRestaurantTable = new DtoRestaurantTable();
		
		BeanUtils.copyProperties(restaurantTable, dtoRestaurantTable);
		return dtoRestaurantTable;
	}

	@Override
	public void deleteRestaurantTable(Long id) {
		if(!restaurantTableRepository.existsById(id)) {
			
		}
		restaurantTableRepository.deleteById(id);
	}

	@Override
	public DtoRestaurantTable updateRestaurantTable(Long id, DtoRestaurantTable dtoRestaurantTable) {
		Optional<RestaurantTable> optional = restaurantTableRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		RestaurantTable dbRestaurantTable = optional.get();
		BeanUtils.copyProperties(dtoRestaurantTable, dbRestaurantTable);
		
		RestaurantTable updated = restaurantTableRepository.save(dbRestaurantTable);
		
		DtoRestaurantTable response = new DtoRestaurantTable();
		
		BeanUtils.copyProperties(updated, response);
		return response;
	}

}
