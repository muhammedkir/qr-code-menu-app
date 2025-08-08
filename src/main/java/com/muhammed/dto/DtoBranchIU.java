package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoBranchIU {

	private String name;
	
    private String address;
    
    private String phoneNumber;
    
    private String logoPath;
    
	private Long userId;
	
	private String restaurantCode;
    
}
