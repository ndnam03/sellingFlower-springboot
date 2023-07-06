package com.vnstart.library.service;

import com.vnstart.library.dto.ProductDto;
import com.vnstart.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(MultipartFile imageProduct,ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
    ProductDto getById(Long id);
    Page<ProductDto> pageProducts(int pageNo);
    Page<ProductDto> searchProducts(int pageNo, String keyword);

    // customer
    List<Product> findTop4ProductsBest();

    List<Product> findTop4ProductsNew();

    Page<Product> findProductsByCategoryID(Long id);

    Page<ProductDto> findProductsByCostPrices(Double min,Double max,int pageNo);

    Product getProductById(Long id);
    List<Product> findRandomProductsByCategoryId(Long categoryId);

}
