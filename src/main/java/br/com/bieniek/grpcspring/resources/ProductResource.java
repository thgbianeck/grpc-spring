package br.com.bieniek.grpcspring.resources;

import br.com.bieniek.*;
import br.com.bieniek.grpcspring.service.IProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductResource extends ProductServiceGrpc.ProductServiceImplBase {

    private final IProductService productService;

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        super.create(request, responseObserver);
    }

    @Override
    public void findById(RequestById request, StreamObserver<ProductResponse> responseObserver) {
        super.findById(request, responseObserver);
    }

    @Override
    public void delete(RequestById request, StreamObserver<ProductResponse> responseObserver) {
        super.delete(request, responseObserver);
    }

    @Override
    public void findAll(EmptyRequest request, StreamObserver<ProductResponseList> responseObserver) {
        super.findAll(request, responseObserver);
    }
}
