package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class dtoLoginRequest {

	 private String email;
	 
	 private String password;
	 
	 private String username;
	 
	 private String phoneNumber;
	 
	 private String Address;
}
