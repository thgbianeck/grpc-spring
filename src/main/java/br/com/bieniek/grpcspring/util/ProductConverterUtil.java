package br.com.bieniek.grpcspring.util;

import br.com.bieniek.grpcspring.domain.Product;
import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import br.com.bieniek.grpcspring.dto.ProductOutputDTO;

public final class ProductConverterUtil {

    public static ProductOutputDTO productToProductOutputDTO(Product product) {
        return ProductOutputDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .build();
    }

    public static Product productInputDtoToProduct(ProductInputDTO productInputDTO) {
        return Product.builder()
                .name(productInputDTO.getName())
                .price(productInputDTO.getPrice())
                .quantityInStock(productInputDTO.getQuantityInStock())
                .build();
    }
}
