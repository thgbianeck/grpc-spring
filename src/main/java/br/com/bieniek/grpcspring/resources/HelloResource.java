package br.com.bieniek.grpcspring.resources;

import br.com.bieniek.HelloRequest;
import br.com.bieniek.HelloResponse;
import br.com.bieniek.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloResource extends HelloServiceGrpc.HelloServiceImplBase{

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        var response = HelloResponse.newBuilder()
                .setMessage(request.getMessage().concat(" from gRPC server"))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
