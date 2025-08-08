package com.muhammed.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhammed.controller.IQrCodeController;
import com.muhammed.dto.DtoQrCode;
import com.muhammed.service.IQrCodeService;

@RestController
@RequestMapping("/rest/api/qrCode")
public class QrCodeControllerImpl implements IQrCodeController{

	@Autowired
	private IQrCodeService qrCodeService;
	
	@PostMapping(path="/save")
	@Override
	public DtoQrCode saveQrCode(DtoQrCode dtoQrCode) {
		return qrCodeService.saveQrCode(dtoQrCode);
	}

	@GetMapping(path="/list")
	@Override
	public List<DtoQrCode> getAllQrCodes() {
		return qrCodeService.getAllQrCodes();
	}

	@GetMapping(path="/list/{id}")
	@Override
	public DtoQrCode getQrCodeById(Long id) {
		return qrCodeService.getQrCodeById(id);
	}

	@DeleteMapping(path="/delete/{id}")
	@Override
	public void deleteQrCode(Long id) {
		qrCodeService.deleteQrCode(id);
	}

	@PutMapping(path="/update/{id}")
	@Override
	public DtoQrCode updateQrCode(Long id, DtoQrCode dtoQrCode) {
		return qrCodeService.updateQrCode(id, dtoQrCode);
	}

}
