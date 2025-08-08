package com.muhammed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoCategory;
import com.muhammed.dto.DtoCreateCategoryRequest;
import com.muhammed.dto.DtoCreateCategoryResponse;

public interface ICategoryController {

	public DtoCategory saveCategory(DtoCategory dtoCategory);
	
	public List<DtoCategory> getAllCategories();
	
	public DtoCategory getCategoryById(Long id);
	
	public void deleteCategory(Long id);
	
	public ResponseEntity<DtoCategory> updateCategory(Long id, DtoCategory dtoCategory);
	
	public ResponseEntity<DtoCreateCategoryResponse> createCategory(DtoCreateCategoryRequest request);
	
	public ResponseEntity<List<DtoCreateCategoryResponse>> getCategoriesByBranchId(Long branchId);
	
	public ResponseEntity<DtoCategory> createCategoryWithİmage(String name, Long branchId, MultipartFile imageFile);
}
