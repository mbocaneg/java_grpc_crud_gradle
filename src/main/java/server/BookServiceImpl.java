package server;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import models.AuthorOuterClass;
import models.BookOuterClass;
import services.BookServiceGrpc;
import services.ServiceParams;

import java.util.List;

public class BookServiceImpl extends BookServiceGrpc.BookServiceImplBase {

    BookstoreRepo repo = BookstoreRepo.getInstance();

    @Override
    public void createBook(BookOuterClass.Book request, StreamObserver<BookOuterClass.Book> responseObserver) {
        BookOuterClass.Book book = repo.createBook(request);
        responseObserver.onNext(book);
        responseObserver.onCompleted();
    }

    @Override
    public void getBook(ServiceParams.BookRequestId request, StreamObserver<BookOuterClass.Book> responseObserver) {
        int id = request.getId();
        BookOuterClass.Book book = repo.getBook(id);
        responseObserver.onNext(book);
        responseObserver.onCompleted();
    }

    @Override
    public void getBookAuthor(ServiceParams.BookRequestId request, StreamObserver<AuthorOuterClass.Author> responseObserver) {
        int id = request.getId();
        BookOuterClass.Book book = repo.getBook(id);
        if(book == null){
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        }
        List<AuthorOuterClass.Author> authors = book.getAuthorsList();
        for(AuthorOuterClass.Author author: authors) {
            responseObserver.onNext(author);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooks(Empty request, StreamObserver<BookOuterClass.Book> responseObserver) {
        for(BookOuterClass.Book book: repo.getAllBooks()) {
            responseObserver.onNext(book);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooksByAuthor(ServiceParams.AuthorRequestId request, StreamObserver<BookOuterClass.Book> responseObserver) {
        super.getAllBooksByAuthor(request, responseObserver);
    }

    @Override
    public void updateBook(ServiceParams.BookUpdateParams request, StreamObserver<BookOuterClass.Book> responseObserver) {
        super.updateBook(request, responseObserver);
    }

    @Override
    public void deleteBook(ServiceParams.BookRequestId request, StreamObserver<Empty> responseObserver) {
        super.deleteBook(request, responseObserver);
    }
}
