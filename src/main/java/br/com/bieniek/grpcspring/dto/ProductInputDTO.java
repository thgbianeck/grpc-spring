package br.com.bieniek.grpcspring.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductInputDTO {

    private final String name;
    private final Double price;
    private final Integer quantityInStock;
}
