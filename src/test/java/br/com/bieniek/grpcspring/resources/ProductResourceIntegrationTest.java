package br.com.bieniek.grpcspring.resources;

import br.com.bieniek.*;
import br.com.bieniek.grpcspring.exception.ProductNotFoundException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static br.com.bieniek.ProductServiceGrpc.ProductServiceBlockingStub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
class ProductResourceIntegrationTest {

    @GrpcClient("inProcess")
    private ProductServiceBlockingStub serviceBlockingStub;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setup() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("when valid data is provided a product is created")
    public void createProductSuccessTest() {

        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("product name")
                .setPrice(10.0)
                .setQuantityInStock(10)
                .build();

        ProductResponse productResponse = serviceBlockingStub.create(productRequest);

        assertThat(productRequest)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "price", "quantity")
                .isEqualTo(productResponse);
    }

    @Test
    @DisplayName("when create is called with duplicated name, then throw ProductAlreadyExistsException")
    public void createProductAlreadyExistsExceptionTest() {

        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("Product A")
                .setPrice(10.0)
                .setQuantityInStock(10)
                .build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(productRequest))
                .withMessageContaining("ALREADY_EXISTS: Produto Product A já cadastrado no sistema");
    }

    @Test
    @DisplayName("when findById is called with valid id, then return a product")
    public void findByIdSuccessTest() {

        RequestById requestById = RequestById.newBuilder()
                .setId(1L)
                .build();

        ProductResponse productResponse = serviceBlockingStub.findById(requestById);

        assertThat(productResponse.getId()).isEqualTo(requestById.getId());
        assertThat(productResponse.getName()).isEqualTo("Product A");
    }

    @Test
    @DisplayName("when findById is called with invalid id, then throw ProductNotFoundException")
    public void findByIdExceptionTest() {

        long id = 999L;

        RequestById requestById = RequestById.newBuilder()
                .setId(id)
                .build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.findById(requestById))
                .withMessageContaining("NOT_FOUND: Produto com ID " + id + " não encontrado");
    }

    @Test
    @DisplayName("when delete is called with valid id, should does not throw exception")
    public void deleteSuccessTest() {

        long id = 1L;

        RequestById requestById = RequestById.newBuilder()
                .setId(id)
                .build();

        Assertions.assertThatNoException()
                .isThrownBy(() -> serviceBlockingStub.delete(requestById));
    }

    @Test
    @DisplayName("when delete is called with invalid id, then throw ProductNotFoundException")
    public void deleteExceptionTest() {

        long id = 999L;

        RequestById requestById = RequestById.newBuilder()
                .setId(id)
                .build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.delete(requestById))
                .withMessageContaining("NOT_FOUND: Produto com ID " + id + " não encontrado");
    }

    @Test
    @DisplayName("when findAll is called, then return a list of products")
    public void findAllSuccessTest() {

        EmptyRequest request = EmptyRequest.newBuilder().build();

        ProductResponseList responseList = serviceBlockingStub.findAll(request);

        assertThat(responseList).isInstanceOf(ProductResponseList.class);
        assertThat(responseList.getProductsList()).isNotEmpty();
        assertThat(responseList.getProductsCount()).isEqualTo(2);

        assertThat(responseList.getProductsList())
                .extracting("id", "name", "price", "quantityInStock")
                .contains(
                        tuple(1L, "Product A", 10.99, 10),
                        tuple(2L, "Product B", 10.99, 10)
                );


    }

}