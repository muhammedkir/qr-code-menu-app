package com.muhammed.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoProduct;

public interface IProductController {
	
	public DtoProduct saveProduct(DtoProduct dtoProduct);

	public List<DtoProduct> getAllProducts();
	
	public DtoProduct getProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public DtoProduct updateProduct(Long id, DtoProduct dtoProduct);
	
	public ResponseEntity<List<DtoProduct>> getProductsByBranchAndCategory(Long branchId, Long categoryId);
	
	public ResponseEntity<List<DtoProduct>> getProductsByCategoryId(Long categoryId);
	
	public ResponseEntity<String> uploadProductWithImage(String name, double price, String description,Long categoryId,Long branchId, MultipartFile imageFile);
}
