package com.muhammed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammed.model.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

	public List<Product> findByBranchIdAndCategoryId(Long branchId, Long categoryId);
	
	public List<Product> findByCategoryId(Long categoryId);

}
