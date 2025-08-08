package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.controller.IProductController;
import com.muhammed.dto.DtoProduct;
import com.muhammed.service.IProductService;

@RestController
@RequestMapping("/rest/api/product")
public class ProductControllerImpl implements IProductController{

	@Autowired
	private IProductService productService;

	@PostMapping(path="/save")
	@Override
	public DtoProduct saveProduct(@RequestBody DtoProduct dtoProduct) {
		return productService.saveProduct(dtoProduct);
	}

	@GetMapping(path="/list")
	@Override
	public List<DtoProduct> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping(path="/list/{id}")
	@Override
	public DtoProduct getProductById(Long id) {
		return productService.getProductById(id);
	}

	@DeleteMapping(path="/delete/{id}")
	@Override
	public void deleteProduct(Long id) {
		productService.deleteProduct(id);
	}

	@PutMapping(path="/update/{id}")
	@Override
	public DtoProduct updateProduct(Long id, DtoProduct dtoProduct) {
		return productService.updateProduct(id, dtoProduct);
	}

	@GetMapping(path="/by-branch-and-category")
	@Override
	public ResponseEntity<List<DtoProduct>> getProductsByBranchAndCategory(@RequestParam Long branchId,@RequestParam Long categoryId) {
		List<DtoProduct> products = productService.getProductsByBranchAndCategory(branchId, categoryId);
	    return ResponseEntity.ok(products);
	}
	
	@Override
	@GetMapping("/by-category")
	public ResponseEntity<List<DtoProduct>> getProductsByCategoryId(@RequestParam Long categoryId) {
	    return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
	}
	
	@Override
	@PostMapping("/upload")
	public ResponseEntity<String> uploadProductWithImage(
	        @RequestParam("name") String name,
	        @RequestParam("price") double price,
	        @RequestParam("description") String description,
	        @RequestParam("categoryId") Long categoryId,
	        @RequestParam("branchId") Long branchId,
	        @RequestParam("image") MultipartFile imageFile
	) {
	    String response = productService.uploadProductWithImage(name, price, description, categoryId, branchId, imageFile);
	    return ResponseEntity.ok(response);
	}
}
