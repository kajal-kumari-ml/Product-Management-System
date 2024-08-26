package com.management.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.project.entity.ProductInformation;
import com.management.project.exception.BadRequest;
import com.management.project.service.ProductService;

/**
 * REST controller for managing product information.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    /**
     * Retrieves a list of all products.
     *
     * @return a list of ProductInformation objects
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductInformation> product = service.getAllProducts();
            if (product.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product
     * @return the ProductInformation object
     */
    @GetMapping("/product")
    public ResponseEntity<?> getProductById(@RequestParam String productId) {
        try {
            ProductInformation product = service.getProductById(productId).get();
            if (product == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /**
     * Adds a new product.
     *
     * @param product the product to be added
     * @return the added ProductInformation object
     * @throws BadRequest if the product details are invalid
     */
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Validated ProductInformation request) {
        try {
            if (service.validateProduct(request)) {
                return ResponseEntity.badRequest().body("Product details are missing or invalid");
            }
            ProductInformation product = service.addProduct(request);
            return ResponseEntity.ok().body(product);
        } catch (BadRequest e) {
            return ResponseEntity.ok().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Updates an existing product.
     *
     * @param productId      the ID of the product to be updated
     * @param productDetails the updated product details
     * @return the updated ProductInformation object
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam String productId,
            @RequestBody ProductInformation productDetails) {
        try {
            ProductInformation productInformation = service.updateProduct(productId, productDetails);
            return ResponseEntity.ok().body(productInformation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to be deleted
     * @return a message indicating the result of the operation
     */

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam String productId) {
        try {
            service.deleteProduct(productId);
            return ResponseEntity.ok().body("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Searches products by name.
     *
     * @param name the name of the product
     * @return a list of matching ProductInformation objects
     */
    @GetMapping("/search/name")
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        try {
            List<ProductInformation> result = service.searchByName(name);
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Searches products by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of matching ProductInformation objects
     */
    @GetMapping("/search/price")
    public ResponseEntity<?> searchByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        try {
            List<ProductInformation> result = service.searchByPriceRange(minPrice, maxPrice);
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Searches products by name and price range.
     *
     * @param name     the name of the product
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of matching ProductInformation objects
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchByNameAndPrice(@RequestParam String name, @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        try {
            List<ProductInformation> result = service.searchByNameAndPrice(name, minPrice, maxPrice);
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
