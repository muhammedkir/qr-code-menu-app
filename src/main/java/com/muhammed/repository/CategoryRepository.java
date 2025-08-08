package com.muhammed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammed.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{

	List<Category> findByBranchId(Long id);
}
