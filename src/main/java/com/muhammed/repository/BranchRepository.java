package com.muhammed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammed.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long>{

	public List<Branch> findByUserId(Long userId);
	
	public Optional<Branch> findByRestaurantCode(String restaurantCode);

}
