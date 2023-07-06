package com.vnstart.library.service.impl;

import com.vnstart.library.dto.ProductDto;
import com.vnstart.library.model.Product;
import com.vnstart.library.repository.ProductRepository;
import com.vnstart.library.service.ProductService;
import com.vnstart.library.utils.ImageUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = new Product();
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if (imageUpload.uploadImage(imageProduct)) {
                    System.out.println("Thanh cong");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.getById(productDto.getId());
            if (imageProduct == null) {
                product.setImage(product.getImage());
            } else {
                if (imageUpload.checkExisted(imageProduct) == false) {
//                    System.out.println("Upload to folder");
                    imageUpload.uploadImage(imageProduct);
                }
//                System.out.println("Image exitsted");
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setSalePrice(productDto.getSalePrice());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
//            System.out.println(product.getName() + ": " + product.getCategory());

            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getById(id);
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<ProductDto> products = transfer(productRepository.findAll());
        Page<ProductDto> productPages = toPage(products, pageable);
        return productPages;
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(0, 6);
        List<ProductDto> productDtoList =transfer(productRepository.searchProductsList(keyword));
        Page<ProductDto> products = toPage(productDtoList,pageable);
        return products;
    }

    @Override
    public List<Product> findTop4ProductsBest() {
        return productRepository.findTop4ProductsBest();
    }

    @Override
    public List<Product> findTop4ProductsNew() {
        return productRepository.findTop4ProductsNew();
    }

    @Override
    public Page<Product> findProductsByCategoryID(Long id) {
        Pageable pageable = PageRequest.of(0, 6);
        return productRepository.findProductsByCategoryID(id,pageable);
    }

    @Override
    public Page<ProductDto> findProductsByCostPrices(Double min, Double max,int pageNo) {
        Pageable pageable = PageRequest.of(pageNo,6);
        List<ProductDto> list = transfer(productRepository.findProductsByCostPrices(min,max));
        Page<ProductDto> products = toPage(list,pageable);
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> findRandomProductsByCategoryId(Long categoryId) {
        Pageable pageable = PageRequest.of(0, 4);
        return productRepository.findRandomProductsByCategoryId(categoryId, pageable);
    }


    public List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.is_deleted());
            productDto.setActivated(product.is_activated());
            productDtoList.add(productDto);
        }

        return productDtoList;
    }

    private Page toPage(List<ProductDto> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) Math.min(startIndex + pageable.getPageSize(), list.size());

        List subList = list.subList(startIndex, endIndex);

        return new PageImpl(subList, pageable, list.size());

    }
}