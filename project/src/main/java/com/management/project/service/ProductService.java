package com.management.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.project.entity.ProductInformation;
import com.management.project.exception.BadRequest;
import com.management.project.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing product information.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products.
     *
     * @return a list of ProductInformation objects
     */
    public List<ProductInformation> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product
     * @return an Optional containing the ProductInformation object, if found
     */
    public Optional<ProductInformation> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    /**
     * Adds a new product.
     *
     * @param product the product to be added
     * @return the added ProductInformation object
     * @throws BadRequest if the product details are invalid
     */
    public ProductInformation addProduct(ProductInformation product) throws BadRequest {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new BadRequest("Failed to add product: " + e.getMessage());
        }
    }

    /**
     * Updates an existing product.
     *
     * @param productId      the ID of the product to be updated
     * @param productDetails the updated product details
     * @return the updated ProductInformation object
     * @throws BadRequest if the update request is invalid
     */
    public ProductInformation updateProduct(String productId, ProductInformation productDetails) throws BadRequest {
        try {
            Optional<ProductInformation> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new BadRequest("Product not found with ID: " + productId);
            }
            if (productDetails.getPrice() <= 0) {
                throw new BadRequest("Price cannot be less than or equal to 0");
            }
            ProductInformation existingProduct = product.get();
            updateProductDetails(existingProduct, productDetails);
        return productRepository.save(existingProduct);
            
        } catch (Exception e) {
            throw new BadRequest("Failed to update product: " + e.getMessage());
        }
        
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to be deleted
     * @throws BadRequest 
     */
    public void deleteProduct(String productId) throws BadRequest {
        try {
            Optional<ProductInformation> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new BadRequest("Product not found with ID: " + productId);
            }
            productRepository.deleteById(productId);

        } catch (Exception e) {
            throw new BadRequest("Failed to delete product: " + e.getMessage());
        }
    }

    /**
     * Searches products by name.
     *
     * @param name the name of the product
     * @return a list of matching ProductInformation objects
     * @throws BadRequest 
     */
    public List<ProductInformation> searchByName(String name) throws BadRequest {
        try{
        return productRepository.findByNameIgnoreCaseContaining(name);
        }catch(Exception e){
            throw new BadRequest("Failed to search product by name: " + e.getMessage());
        }
    }

    /**
     * Searches products by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of matching ProductInformation objects
     * @throws BadRequest 
     */
    public List<ProductInformation> searchByPriceRange(double minPrice, double maxPrice) throws BadRequest {
      try{
        return productRepository.findByPriceBetween(minPrice, maxPrice);
      }catch(Exception e){
          throw new BadRequest("Failed to search product by price range: " + e.getMessage());
      }
    }

    /**
     * Searches products by name and price range.
     *
     * @param name     the name of the product
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of matching ProductInformation objects
     * @throws BadRequest 
     */
    public List<ProductInformation> searchByNameAndPrice(String name, double minPrice, double maxPrice) throws BadRequest {
       try{
        return productRepository.findByNameAndPriceRange(name, minPrice, maxPrice);
       }catch(Exception e){
           throw new BadRequest("Failed to search product by name and price range: " + e.getMessage());
       }
    }

    /**
     * Validates the product details.
     *
     * @param product the product to validate
     * @throws BadRequest if the product details are invalid
     */
    public Boolean validateProduct(ProductInformation product)  {
        if (product.getDescription() == null || product.getName() == null || product.getPrice() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Updates the details of a product.
     *
     * @param product        the existing product to update
     * @param productDetails the updated product details
     * @throws BadRequest if the product details are invalid
     */
    private void updateProductDetails(ProductInformation product, ProductInformation productDetails) {
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
       
        if (productDetails.getPrice() > 0) {
            product.setPrice(productDetails.getPrice());
        }
    
}
}
