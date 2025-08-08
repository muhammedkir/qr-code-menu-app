package com.muhammed.controller;

import java.util.List;

import com.muhammed.dto.DtoQrCode;

public interface IQrCodeController {

	public DtoQrCode saveQrCode(DtoQrCode dtoQrCode);
	
	public List<DtoQrCode> getAllQrCodes();
	
	public DtoQrCode getQrCodeById(Long id);
	
	public void deleteQrCode(Long id);
	
	public DtoQrCode updateQrCode(Long id, DtoQrCode dtoQrCode);
}
