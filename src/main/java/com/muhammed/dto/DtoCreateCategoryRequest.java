package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCreateCategoryRequest {

	 private String name;
	 
	 private String imagePath;
	 
	 private Long branchId;
}
