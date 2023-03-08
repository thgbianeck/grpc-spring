package br.com.bieniek.grpcspring.exception;

import io.grpc.Status;

import static io.grpc.Status.NOT_FOUND;

public class ProductNotFoundException extends BaseBusinessException{

    private static final String ERROR_MESSAGE = "Produto com ID %s não encontrado";
    private final Long id;

    public ProductNotFoundException(Long id) {
        super(String.format(ERROR_MESSAGE, id));
        this.id = id;
    }

    @Override
    public Status getStatusCode() {
        return NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return String.format(ERROR_MESSAGE, this.id);
    }
}
