package server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import models.AuthorOuterClass;
import services.AuthorServiceGrpc;
import services.ServiceParams;

public class AuthorServiceImpl extends AuthorServiceGrpc.AuthorServiceImplBase {

    BookstoreRepo repo = BookstoreRepo.getInstance();

    @Override
    public void createAuthor(AuthorOuterClass.Author request, StreamObserver<AuthorOuterClass.Author> responseObserver) {
        System.out.println("[SERVER] Create Author request: " + request);
        AuthorOuterClass.Author author = repo.createAuthor(request);
        responseObserver.onNext(author);
        responseObserver.onCompleted();
    }

    @Override
    public void getAuthor(ServiceParams.AuthorRequestId request, StreamObserver<AuthorOuterClass.Author> responseObserver) {
        int id = request.getId();
        AuthorOuterClass.Author author = repo.getAuthor(id);
        responseObserver.onNext(author);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAuthors(Empty request, StreamObserver<AuthorOuterClass.Author> responseObserver) {
        System.out.println("[SERVER] Get all Authors request");
        for(AuthorOuterClass.Author author: repo.getAllAuthors() ) {
            responseObserver.onNext(author);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateAuthor(ServiceParams.AuthorUpdateParams request, StreamObserver<AuthorOuterClass.Author> responseObserver) {
        AuthorOuterClass.Author authorToUpdate = request.getAuthorToUpdate();
        AuthorOuterClass.Author updatedAuthor = repo.updateAuthor(authorToUpdate);
        responseObserver.onNext(updatedAuthor);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAuthor(ServiceParams.AuthorRequestId request, StreamObserver<Empty> responseObserver) {
        super.deleteAuthor(request, responseObserver);
    }
}
