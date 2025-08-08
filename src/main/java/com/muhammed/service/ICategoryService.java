package com.muhammed.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoCategory;
import com.muhammed.dto.DtoCreateCategoryRequest;
import com.muhammed.dto.DtoCreateCategoryResponse;

public interface ICategoryService {

	public DtoCategory saveCategory(DtoCategory dtoCategory);
	
	public List<DtoCategory> getAllCategories();
	
	public DtoCategory getCategoryById(Long id);
	
	public void deleteCategory(Long id);
	
	public DtoCategory updateCategory(Long id, DtoCategory dtoCategory);
	
	public DtoCreateCategoryResponse createCategory(DtoCreateCategoryRequest request);

	public List<DtoCreateCategoryResponse> getCategoriesByfindByBranchId(Long branchId);
	
	public DtoCategory createCategoryWithImage(String name, Long branchId, MultipartFile imageFile);

}
