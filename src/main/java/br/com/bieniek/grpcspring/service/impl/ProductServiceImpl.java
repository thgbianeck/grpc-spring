package br.com.bieniek.grpcspring.service.impl;

import br.com.bieniek.grpcspring.domain.Product;
import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import br.com.bieniek.grpcspring.dto.ProductOutputDTO;
import br.com.bieniek.grpcspring.exception.ProductAlreadyExistsException;
import br.com.bieniek.grpcspring.exception.ProductNotFoundException;
import br.com.bieniek.grpcspring.repository.ProductRepository;
import br.com.bieniek.grpcspring.service.IProductService;
import br.com.bieniek.grpcspring.util.ProductConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.bieniek.grpcspring.util.ProductConverterUtil.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        checkDuplicity(inputDTO.getName());
        var product = productInputDtoToProduct(inputDTO);
        var productCreated = productRepository.save(product);

        return productToProductOutputDTO(productCreated);
    }

    @Override
    public ProductOutputDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductConverterUtil.productToProductOutputDTO(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    @Override
    public List<ProductOutputDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductConverterUtil::productToProductOutputDTO)
                .toList();
    }

    private void checkDuplicity(String name) {
        productRepository.findByNameIgnoreCase(name).ifPresent(e -> {
            throw new ProductAlreadyExistsException(name);
        });
    }
}
