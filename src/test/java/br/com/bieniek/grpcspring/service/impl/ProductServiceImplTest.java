package br.com.bieniek.grpcspring.service.impl;

import br.com.bieniek.grpcspring.domain.Product;
import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import br.com.bieniek.grpcspring.dto.ProductOutputDTO;
import br.com.bieniek.grpcspring.exception.ProductAlreadyExistsException;
import br.com.bieniek.grpcspring.exception.ProductNotFoundException;
import br.com.bieniek.grpcspring.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    @DisplayName("When create product is called with valid data, then a product should be returned")
    void createProductSuccessTest() {
        Product product = Product.builder()
                .id(1L)
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        when(productRepository.save(any())).thenReturn(product);

        ProductInputDTO inputDTO = ProductInputDTO.builder()
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        ProductOutputDTO outputDTO = service.create(inputDTO);

        assertThat(outputDTO).usingRecursiveComparison().isEqualTo(product);
    }

    @Test
    @DisplayName("When create product service is call with dupllcated name, then thrown ProductAlreadyExistsException")
    void createProductExceptionTest() {
        Product product = Product.builder()
                .id(1L)
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        when(productRepository.findByNameIgnoreCase(any()))
                .thenReturn(Optional.of(product));

        ProductInputDTO inputDTO = ProductInputDTO.builder()
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        assertThatExceptionOfType(ProductAlreadyExistsException.class)
                .isThrownBy(() -> service.create(inputDTO));
    }

    @Test
    @DisplayName("When findById product with valid id, then return a product")
    void findByIdSuccessTest() {
        Long id = 1L;

        Product product = Product.builder()
                .id(id)
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        ProductOutputDTO outputDTO = service.findById(id);

        assertThat(outputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    @DisplayName("When findById product is called with invalid id, then throw ProductNotFoundException")
    void findByIdtExceptionTest() {
        long id = 1L;

        when(productRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> service.findById(id));
    }

}