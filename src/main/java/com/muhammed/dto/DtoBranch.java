package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoBranch {
	
	private Long id;

	private String name;
	
	private String address;
	
	private String phoneNumber;
	
	private String logoPath;

	private Long userId;
	
	private String restaurantCode;
}
