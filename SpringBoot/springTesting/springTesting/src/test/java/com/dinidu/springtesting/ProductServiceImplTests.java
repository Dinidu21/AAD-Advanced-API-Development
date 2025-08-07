package com.dinidu.springtesting;

import com.dinidu.springtesting.entity.Product;
import com.dinidu.springtesting.repo.ProductRepository;
import com.dinidu.springtesting.service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
                .pid(1L)
                .name("Test Product")
                .description("This is a test product")
                .price(100.0)
                .quantity(10)
                .build();
    }

    @Test
    public void testGetAllProducts() {
        List<Product> productList = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProduct();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getPid(), result.get(0).getPid());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductByIdFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals(product.getPid(), result.getPid());
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        assertEquals("Product not found with id: 1", exception.getMessage());
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);
        assertEquals(product.getPid(), createdProduct.getPid());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductFound() {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        product.setName("Updated Product");

        Product updatedProduct = productService.updateProduct(1L, product);
        assertNotNull(updatedProduct);
        assertEquals(1L, updatedProduct.getPid());
        assertEquals("Updated Product", updatedProduct.getName());

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProductNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);
        Exception exception = assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, product));
        assertEquals("Product not found with id: 1", exception.getMessage());

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    public void testDeleteProductFound() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProductNotFound() {
        when(productRepository.existsById(1L)).thenReturn(false);
        Exception exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(1L));
        assertEquals("Product not found with id: 1", exception.getMessage());

        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(0)).deleteById(any());
    }
}