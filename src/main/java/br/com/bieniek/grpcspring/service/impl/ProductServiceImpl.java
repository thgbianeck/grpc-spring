package br.com.bieniek.grpcspring.service.impl;

import br.com.bieniek.grpcspring.domain.Product;
import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import br.com.bieniek.grpcspring.dto.ProductOutputDTO;
import br.com.bieniek.grpcspring.repository.ProductRepository;
import br.com.bieniek.grpcspring.service.IProductService;
import br.com.bieniek.grpcspring.util.ProductConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.bieniek.grpcspring.util.ProductConverterUtil.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        var product = productInputDtoToProduct(inputDTO);
        var productCreated = productRepository.save(product);

        return productToProductOutputDTO(productCreated);
    }

    @Override
    public ProductOutputDTO findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ProductOutputDTO> findAll() {
        return null;
    }
}
