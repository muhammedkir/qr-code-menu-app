package com.muhammed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLogoUploadRequest {
	
    private Long branchId;
    
    private String logoPath;

    // getters and setters
}
