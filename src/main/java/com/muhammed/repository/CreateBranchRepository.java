package com.muhammed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammed.model.CreateBranchRequest;

public interface CreateBranchRepository extends JpaRepository<CreateBranchRequest, Long>{

}
