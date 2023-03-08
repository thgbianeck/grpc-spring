package br.com.bieniek.grpcspring.resources;

import br.com.bieniek.ProductRequest;
import br.com.bieniek.ProductResponse;
import br.com.bieniek.ProductServiceGrpc;
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

import static br.com.bieniek.ProductServiceGrpc.*;
import static org.assertj.core.api.Assertions.*;

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
                .setQuantity(10)
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
                .setQuantity(10)
                .build();

        Assertions.assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> serviceBlockingStub.create(productRequest))
                .withMessageContaining("ALREADY_EXISTS: Produto Product A já cadastrado no sistema");
    }

}