package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String phoneNumber;
	
	private String Address;
}
