package com.muhammed.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoProduct;

public interface IProductService {

public DtoProduct saveProduct(DtoProduct dtoProduct);
	
	public List<DtoProduct> getAllProducts();
	
	public DtoProduct getProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public DtoProduct updateProduct(Long id, DtoProduct dtoProduct);
	
	public List<DtoProduct> getProductsByBranchAndCategory(Long branchId, Long categoryId);
	
	public List<DtoProduct> getProductsByCategoryId(Long categoryId);
	
	public String uploadProductWithImage(String name, double price, String description, Long categoryId,Long branchId, MultipartFile imageFile);
}
