package com.muhammed.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhammed.dto.DtoQrCode;
import com.muhammed.model.QrCode;
import com.muhammed.repository.QrCodeRepository;
import com.muhammed.service.IQrCodeService;

@Service
public class QrCodeServiceImpl  implements IQrCodeService{

	@Autowired
	private QrCodeRepository qrCodeRepository;
	
	
	@Override
	public DtoQrCode saveQrCode(DtoQrCode dtoQrCode) {
		QrCode qrCode = new QrCode();
		BeanUtils.copyProperties(dtoQrCode, qrCode);
		
		DtoQrCode response = new DtoQrCode();
		QrCode dbQrCode = qrCodeRepository.save(qrCode);
		BeanUtils.copyProperties(dbQrCode, response);
		return response;
	}

	@Override
	public List<DtoQrCode> getAllQrCodes() {
		List<QrCode> qrCodeList = qrCodeRepository.findAll();
		List<DtoQrCode> dtoList = new ArrayList<>();
		
		for (QrCode qrCode : qrCodeList) {
			DtoQrCode dto = new DtoQrCode();
			BeanUtils.copyProperties(qrCode, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public DtoQrCode getQrCodeById(Long id) {
		Optional<QrCode> optional = qrCodeRepository.findById(id);
		
		if(optional.isEmpty()) {
			return null;
		}
		QrCode qrCode = optional.get();
		DtoQrCode dtoQrCode = new DtoQrCode();
		
		BeanUtils.copyProperties(qrCode, dtoQrCode);
		return dtoQrCode;
	}

	@Override
	public void deleteQrCode(Long id) {
		if(!qrCodeRepository.existsById(id)) {
			
		}
		qrCodeRepository.deleteById(id);
	}

	@Override
	public DtoQrCode updateQrCode(Long id, DtoQrCode dtoQrCode) {
		Optional<QrCode> optional = qrCodeRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		QrCode dbQrCode = optional.get();
		BeanUtils.copyProperties(dtoQrCode, dbQrCode);
		
		QrCode updated = qrCodeRepository.save(dbQrCode);
		
		DtoQrCode response = new DtoQrCode();
		
		BeanUtils.copyProperties(updated, response);		
		return response;
	}

}
