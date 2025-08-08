package com.muhammed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammed.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	List<Menu> findByBranchId(Long branchId);
}
