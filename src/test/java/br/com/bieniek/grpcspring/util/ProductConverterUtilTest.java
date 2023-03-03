package br.com.bieniek.grpcspring.util;

import br.com.bieniek.grpcspring.domain.Product;
import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductConverterUtilTest {

    @Test
    @DisplayName("Should convert a Product to a ProductOutputDTO")
    void productToProductOutputDTOTest() {
        var product = Product.builder()
                .id(1L)
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        var productOutputDTO = ProductConverterUtil.productToProductOutputDTO(product);

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productOutputDTO);
    }

    @Test
    @DisplayName("Should convert a ProductInputDTO to a Product")
    void productInputDtoToProductTest() {
        var productInputDTO = ProductInputDTO.builder()
                .name("product name")
                .price(10.0)
                .quantityInStock(10)
                .build();

        var product = ProductConverterUtil.productInputDtoToProduct(productInputDTO);

        Assertions.assertThat(productInputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

}