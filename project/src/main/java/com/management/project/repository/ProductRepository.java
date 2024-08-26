package com.management.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.management.project.entity.ProductInformation;

@Repository
public interface ProductRepository extends MongoRepository<ProductInformation, String> {

    List<ProductInformation> findByNameAndPrice(String name, double price);

      // Find by name (case-insensitive)
    List<ProductInformation> findByNameIgnoreCaseContaining(String name);
    
    // Find by price range
    List<ProductInformation> findByPriceBetween(double minPrice, double maxPrice);
    
    // Custom query to find by name and price range
    @Query("{'name': {$regex: ?0, $options: 'i'}, 'price': {$gte: ?1, $lte: ?2}}")
    List<ProductInformation> findByNameAndPriceRange(String name, double minPrice, double maxPrice);

    
}
