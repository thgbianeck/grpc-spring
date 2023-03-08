package br.com.bieniek.grpcspring.resources;

import br.com.bieniek.*;
import br.com.bieniek.grpcspring.dto.ProductInputDTO;
import br.com.bieniek.grpcspring.dto.ProductOutputDTO;
import br.com.bieniek.grpcspring.service.IProductService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductResource extends ProductServiceGrpc.ProductServiceImplBase {

    private final IProductService productService;

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductInputDTO inputDTO = ProductInputDTO.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantityInStock(request.getQuantity())
                .build();

        ProductOutputDTO outputDTO = this.productService.create(inputDTO);

        ProductResponse response = ProductResponse.newBuilder()
                .setId(outputDTO.getId())
                .setName(outputDTO.getName())
                .setPrice(outputDTO.getPrice())
                .setQuantity(outputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(RequestById request, StreamObserver<ProductResponse> responseObserver) {
        ProductOutputDTO outputDTO = this.productService.findById(request.getId());

        ProductResponse response = ProductResponse.newBuilder()
                .setId(outputDTO.getId())
                .setName(outputDTO.getName())
                .setPrice(outputDTO.getPrice())
                .setQuantity(outputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(RequestById request, StreamObserver<ProductResponse> responseObserver) {
        throw new StatusRuntimeException(Status.UNIMPLEMENTED);
    }

    @Override
    public void findAll(EmptyRequest request, StreamObserver<ProductResponseList> responseObserver) {
        throw new StatusRuntimeException(Status.UNIMPLEMENTED);
    }
}
