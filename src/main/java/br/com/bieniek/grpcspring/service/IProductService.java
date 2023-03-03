package br.com.bieniek.grpcspring.service;

import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import br.com.bieniek.grpcspring.dto.ProductOutputDTO;

import java.util.List;

public interface IProductService {
    ProductOutputDTO create(ProductInputDTO productInputDTO);
    ProductOutputDTO findById(Long id);
    void delete(Long id);
    List<ProductOutputDTO> findAll();
}
