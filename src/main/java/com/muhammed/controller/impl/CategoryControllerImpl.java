package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.controller.ICategoryController;
import com.muhammed.dto.DtoCategory;
import com.muhammed.dto.DtoCreateCategoryRequest;
import com.muhammed.dto.DtoCreateCategoryResponse;
import com.muhammed.service.ICategoryService;

@RestController
@RequestMapping("/rest/api/category")
public class CategoryControllerImpl implements ICategoryController{

	@Autowired
	private ICategoryService categoryService;
	
	@PostMapping(path="/save")
	@Override
	public DtoCategory saveCategory(DtoCategory dtoCategory) {
		return categoryService.saveCategory(dtoCategory);
	}

	@GetMapping(path="/list/categories")
	@Override
	public List<DtoCategory> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping(path="/list")
	@Override
	public DtoCategory getCategoryById(@RequestParam Long id) {
		return categoryService.getCategoryById(id);
	}

	@GetMapping(path="/delete/{id}")
	@Override
	public void deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		
	}

	@PutMapping(path="/update/{id}")
	@Override
	public ResponseEntity<DtoCategory> updateCategory(@PathVariable Long id, @RequestBody DtoCategory dto) {
	    DtoCategory updated = categoryService.updateCategory(id, dto);
	    return ResponseEntity.ok(updated);
	}
	
	@PostMapping("/create")
    public ResponseEntity<DtoCreateCategoryResponse> createCategory(@RequestBody DtoCreateCategoryRequest request) {
		DtoCreateCategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch")
    public ResponseEntity<List<DtoCreateCategoryResponse>> getCategoriesByBranchId(@RequestParam Long branchId) {
        return ResponseEntity.ok(categoryService.getCategoriesByfindByBranchId(branchId));
    }
    
    @Override
    @PostMapping("/upload")
    public ResponseEntity<DtoCategory> createCategoryWithİmage(
            @RequestParam("name") String name,
            @RequestParam("branchId") Long branchId,
            @RequestParam("image") MultipartFile imageFile
    ) {
    	DtoCategory dtoCategory = categoryService.createCategoryWithImage(name, branchId, imageFile);
        return ResponseEntity.ok(dtoCategory);
    }

}
