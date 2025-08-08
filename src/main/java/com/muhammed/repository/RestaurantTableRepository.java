package com.muhammed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muhammed.model.RestaurantTable;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

}
