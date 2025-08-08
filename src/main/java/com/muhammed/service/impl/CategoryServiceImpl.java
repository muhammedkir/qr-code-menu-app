package com.muhammed.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoCategory;
import com.muhammed.dto.DtoCreateCategoryRequest;
import com.muhammed.dto.DtoCreateCategoryResponse;
import com.muhammed.model.Category;
import com.muhammed.repository.CategoryRepository;
import com.muhammed.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public DtoCategory saveCategory(DtoCategory dtoCategory) {
		Category category = new Category();
		BeanUtils.copyProperties(dtoCategory, category);
		
		DtoCategory response = new DtoCategory();
		Category dbCategory = categoryRepository.save(category);
		BeanUtils.copyProperties(dbCategory, response);
		return response;
	}

	@Override
	public List<DtoCategory> getAllCategories() {
		List<Category> categoryList = categoryRepository.findAll();
		List<DtoCategory> dtoList = new ArrayList<>();
		
		for (Category category : categoryList) {
			DtoCategory dto = new DtoCategory();
			BeanUtils.copyProperties(category, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public DtoCategory getCategoryById(Long id) {
		Optional<Category> optional = categoryRepository.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		Category category = optional.get();
		DtoCategory dtoCategory = new DtoCategory();
		
		BeanUtils.copyProperties(category, dtoCategory);
		return dtoCategory;
	}

	@Override
	public void deleteCategory(Long id) {
		if(!categoryRepository.existsById(id)) {
			
		}
		categoryRepository.deleteById(id);	
	}

	@Override
	public DtoCategory updateCategory(Long id, DtoCategory dtoCategory) {
		Optional<Category> optional = categoryRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		Category dbCategory = optional.get();
		BeanUtils.copyProperties(dtoCategory, dbCategory);
		
		Category updated = categoryRepository.save(dbCategory);
		
		DtoCategory response = new DtoCategory();
		
		BeanUtils.copyProperties(updated, response);
		return response;
	}
	
	
	@Override
    public DtoCreateCategoryResponse createCategory(DtoCreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setBranchId(request.getBranchId());

        Category saved = categoryRepository.save(category);

        DtoCreateCategoryResponse dto = new DtoCreateCategoryResponse();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setBranchId(saved.getBranchId());

        return dto;
    }

	@Override
	public List<DtoCreateCategoryResponse> getCategoriesByfindByBranchId(Long branchId) {
	    List<Category> categories = categoryRepository.findByBranchId(branchId);
	    List<DtoCreateCategoryResponse> dtoList = new ArrayList<>();

	    for (Category cat : categories) {
	    	DtoCreateCategoryResponse dto = new DtoCreateCategoryResponse();
	        dto.setId(cat.getId());
	        dto.setName(cat.getName());
	        dto.setBranchId(cat.getBranchId());
	        dto.setImagePath(cat.getImagePath());
	        dtoList.add(dto);
	    }

	    return dtoList;
	}
	
	@Override
	public DtoCategory createCategoryWithImage(String name, Long branchId, MultipartFile imageFile) {
	    try {
	    	String uploadDir = "uploads/";
	    	String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
	    	
	    	Path filePath = Paths.get(uploadDir, fileName);
	    	Files.createDirectories(filePath.getParent());
	    	Files.write(filePath, imageFile.getBytes());
	    	
	    	Category category = new Category();
	    	category.setName(name);
	    	category.setBranchId(branchId);
	    	category.setImagePath(fileName);
	    	
	    	categoryRepository.save(category);
	    	return new DtoCategory(category.getName(),category.getImagePath(),category.getBranchId());
	    	
	    }  catch(IOException e) {
	    	throw new RuntimeException("Dosya yükleme hatası: " + e.getMessage());
	    }
	   
	}

}
