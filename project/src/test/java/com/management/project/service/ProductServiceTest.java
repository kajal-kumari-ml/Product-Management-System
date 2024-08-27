package com.management.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.management.project.entity.ProductInformation;
import com.management.project.exception.BadRequest;
import com.management.project.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<ProductInformation> products = Arrays.asList(
                new ProductInformation("1", "Product 1", "Description 1", 100.0),
                new ProductInformation("2", "Product 2", "Description 2", 200.0)
        );
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductInformation> result = productService.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        // Arrange
        String productId = "1";
        ProductInformation product = new ProductInformation("1", "Product 1", "Description 1", 100.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<ProductInformation> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Product 1", result.get().getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testAddProduct() throws BadRequest {
        // Arrange
        ProductInformation product = new ProductInformation("1", "Product 1", "Description 1", 100.0);
        when(productRepository.save(any(ProductInformation.class))).thenReturn(product);

        // Act
        ProductInformation result = productService.addProduct(product);

        // Assert
        assertNotNull(result);
        assertEquals("Product 1", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testAddProduct_ThrowsBadRequest() {
        // Arrange
        ProductInformation product = new ProductInformation("1", "Product 1", "Description 1", 100.0);
        when(productRepository.save(any(ProductInformation.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.addProduct(product);
        });
        assertEquals("Failed to add product: Database error", exception.getMessage());
    }

    @Test
    void testUpdateProduct() throws BadRequest {
        // Arrange
        String productId = "1";
        ProductInformation existingProduct = new ProductInformation("1", "Product 1", "Description 1", 100.0);
        ProductInformation updatedProductDetails = new ProductInformation("1", "Updated Product", "Updated Description", 150.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(ProductInformation.class))).thenReturn(updatedProductDetails);

        // Act
        ProductInformation result = productService.updateProduct(productId, updatedProductDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_ThrowsBadRequest_WhenProductNotFound() {
        // Arrange
        String productId = "1";
        ProductInformation updatedProductDetails = new ProductInformation("1", "Updated Product", "Updated Description", 150.0);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.updateProduct(productId, updatedProductDetails);
        });
        assertEquals("Product not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateProduct_ThrowsBadRequest_WhenInvalidPrice() {
        // Arrange
        String productId = "1";
        ProductInformation existingProduct = new ProductInformation("1", "Product 1", "Description 1", 100.0);
        ProductInformation updatedProductDetails = new ProductInformation("1", "Updated Product", "Updated Description", -50.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.updateProduct(productId, updatedProductDetails);
        });
        assertEquals("Price cannot be less than or equal to 0", exception.getMessage());
    }

    @Test
    void testDeleteProduct() throws BadRequest {
        // Arrange
        String productId = "1";
        ProductInformation product = new ProductInformation("1", "Product 1", "Description 1", 100.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProduct_ThrowsBadRequest_WhenProductNotFound() {
        // Arrange
        String productId = "1";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.deleteProduct(productId);
        });
        assertEquals("Failed to delete product: Product not found with ID: 1", exception.getMessage());
    }

    @Test
    void testSearchByName() throws BadRequest {
        // Arrange
        String name = "Product";
        List<ProductInformation> products = Arrays.asList(
                new ProductInformation("1", "Product 1", "Description 1", 100.0),
                new ProductInformation("2", "Product 2", "Description 2", 200.0)
        );
        when(productRepository.findByNameIgnoreCaseContaining(name)).thenReturn(products);

        // Act
        List<ProductInformation> result = productService.searchByName(name);

        // Assert
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByNameIgnoreCaseContaining(name);
    }

    @Test
    void testSearchByName_ThrowsBadRequest() {
        // Arrange
        String name = "Product";
        when(productRepository.findByNameIgnoreCaseContaining(name)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.searchByName(name);
        });
        assertEquals("Failed to search product by name: Database error", exception.getMessage());
    }

    @Test
    void testSearchByPriceRange() throws BadRequest {
        // Arrange
        double minPrice = 50.0;
        double maxPrice = 150.0;
        List<ProductInformation> products = Arrays.asList(
                new ProductInformation("1", "Product 1", "Description 1", 100.0)
        );
        when(productRepository.findByPriceBetween(minPrice, maxPrice)).thenReturn(products);

        // Act
        List<ProductInformation> result = productService.searchByPriceRange(minPrice, maxPrice);

        // Assert
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByPriceBetween(minPrice, maxPrice);
    }

    @Test
    void testSearchByPriceRange_ThrowsBadRequest() {
        // Arrange
        double minPrice = 50.0;
        double maxPrice = 150.0;
        when(productRepository.findByPriceBetween(minPrice, maxPrice)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.searchByPriceRange(minPrice, maxPrice);
        });
        assertEquals("Failed to search product by price range: Database error", exception.getMessage());
    }

    @Test
    void testSearchByNameAndPrice() throws BadRequest {
        // Arrange
        String name = "Product";
        double minPrice = 50.0;
        double maxPrice = 150.0;
        List<ProductInformation> products = Arrays.asList(
                new ProductInformation("1", "Product 1", "Description 1", 100.0)
        );
        when(productRepository.findByNameAndPriceRange(name, minPrice, maxPrice)).thenReturn(products);

        // Act
        List<ProductInformation> result = productService.searchByNameAndPrice(name, minPrice, maxPrice);

        // Assert
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByNameAndPriceRange(name, minPrice, maxPrice);
    }

    @Test
    void testSearchByNameAndPrice_ThrowsBadRequest() {
        // Arrange
        String name = "Product";
        double minPrice = 50.0;
        double maxPrice = 150.0;
        when(productRepository.findByNameAndPriceRange(name, minPrice, maxPrice)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        BadRequest exception = assertThrows(BadRequest.class, () -> {
            productService.searchByNameAndPrice(name, minPrice, maxPrice);
        });
        assertEquals("Failed to search product by name and price range: Database error", exception.getMessage());
    }

  
}
