package com.muhammed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muhammed.model.QrCode;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long>{

}
