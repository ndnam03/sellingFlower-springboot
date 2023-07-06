package com.vnstart.library.repository;

import com.vnstart.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    Page<Product> searchProducts(String keyword, Pageable pageable);

    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    List<Product> searchProductsList(String keyword);

    // customer
    @Query(value = "select * from product LIMIT 4",nativeQuery = true)
    List<Product> findTop4ProductsBest();
    @Query(value = "SELECT * FROM product ORDER BY product.product_id   LIMIT 4; ",nativeQuery = true)
    List<Product> findTop4ProductsNew();

    @Query("select p from Product p where  p.is_activated = true  and p.is_deleted = false and p.category.id = ?1")
    Page<Product> findProductsByCategoryID(Long id,Pageable pageable);

    @Query("select p from Product p where p.costPrice >= ?1 and p.costPrice <= ?2 ")
    List<Product> findProductsByCostPrices(Double min,Double max);

    @Query(value = "SELECT * FROM Product  WHERE category_id = ?1",nativeQuery = true)
    List<Product> findRandomProductsByCategoryId(Long categoryId, Pageable pageable);

}
