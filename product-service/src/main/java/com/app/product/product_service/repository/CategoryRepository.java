package com.app.product.product_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.product.product_service.model.ProductCategory;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByName(String categoryName);

}
