package com.muhammed.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserBranchResponse {

	private String username;
	
    private String email;
    
    private List<DtoBranchIU> branches;
}
