package com.muhammed.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCreateMenuResponse {

	private String name; 
	
    private List<Long> productIds; 
    
    private Long branchId;
}
