package com.muhammed.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.muhammed.dto.DtoProduct;
import com.muhammed.model.Product;
import com.muhammed.repository.ProductRepository;
import com.muhammed.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public DtoProduct saveProduct(DtoProduct dtoProduct) {
		Product product = new Product();
		BeanUtils.copyProperties(dtoProduct, product);
		
		DtoProduct response = new DtoProduct();
		Product dbProduct = productRepository.save(product);
		
		BeanUtils.copyProperties(dbProduct, response);
		return response;
	}

	@Override
	public List<DtoProduct> getAllProducts() {
		List<Product> productList = productRepository.findAll();
		List<DtoProduct> dtoList = new ArrayList<>();
		
		for (Product product : productList) {
			DtoProduct dto = new DtoProduct();
			BeanUtils.copyProperties(product, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public DtoProduct getProductById(Long id) {
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		Product product = optional.get();
		DtoProduct dtoProduct = new DtoProduct();
		
		BeanUtils.copyProperties(product, dtoProduct);
		return dtoProduct;
	}

	@Override
	public void deleteProduct(Long id) {
		if(!productRepository.existsById(id)) {
			
		}
		productRepository.deleteById(id);
	}

	@Override
	public DtoProduct updateProduct(Long id, DtoProduct dtoProduct) {
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isEmpty()) {
			
		}
		Product dbProduct = optional.get();
		BeanUtils.copyProperties(dtoProduct, dbProduct);
		
		Product updated = productRepository.save(dbProduct);
		
		DtoProduct response = new  DtoProduct();
		
		BeanUtils.copyProperties(updated, response);
		return response;
	}

	
	@Override
	public List<DtoProduct> getProductsByBranchAndCategory(Long branchId, Long categoryId) {
	    List<Product> productList = productRepository.findByBranchIdAndCategoryId(branchId, categoryId);

	    List<DtoProduct> dtoList = new ArrayList<>();
	    for (Product product : productList) {
	        DtoProduct dto = new DtoProduct();
	        BeanUtils.copyProperties(product, dto);
	        dtoList.add(dto);
	    }

	    return dtoList;
	}
	
	@Override
	public List<DtoProduct> getProductsByCategoryId(Long categoryId) {
	    List<Product> products = productRepository.findByCategoryId(categoryId);
	    List<DtoProduct> dtoList = new ArrayList<>();

	    for (Product product : products) {
	        DtoProduct dto = new DtoProduct();
	        BeanUtils.copyProperties(product, dto);
	        dtoList.add(dto);
	    }

	    return dtoList;
	}

	@Override
    public String uploadProductWithImage(String name, double price, String description, Long categoryId,Long branchId, MultipartFile imageFile) {
        try {
            // 1. Görseli uploads klasörüne kaydet
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, imageFile.getBytes());

            // 2. Ürün oluştur
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setBranchId(branchId);
            product.setImagePath(fileName); // Örnek: /uploads/abc123.jpg

            // 3. Veritabanına kaydet
            productRepository.save(product);

            return "Ürün başarıyla kaydedildi.";
        } catch (IOException e) {
            throw new RuntimeException("Dosya yükleme hatası: " + e.getMessage());
        }
    }	
	

}
