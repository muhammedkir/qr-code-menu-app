package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduct {

	private String name;
	
	private String description;
	
	private Double price;
	
	private String imagePath;
	
	private Long branchId;
	
	private Long categoryId;
	
}
