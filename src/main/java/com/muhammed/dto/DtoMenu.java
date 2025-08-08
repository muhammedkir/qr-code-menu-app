package com.muhammed.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoMenu {

	private Long branchId;
	
	private String name;
	
	private List<Long> productId;
}
