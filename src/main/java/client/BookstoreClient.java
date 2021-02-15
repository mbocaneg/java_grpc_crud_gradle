package client;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import models.AuthorOuterClass;
import models.BookOuterClass;
import services.AuthorServiceGrpc;
import services.BookServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class BookstoreClient {
    ManagedChannel channel;
    AuthorServiceGrpc.AuthorServiceBlockingStub authorStub;
    AuthorServiceGrpc.AuthorServiceStub authorStubAsync;
    BookServiceGrpc.BookServiceBlockingStub bookStub;
    BookServiceGrpc.BookServiceStub bookStubAsync;

    private void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 1234)
                .usePlaintext()
                .build();

        authorStub = AuthorServiceGrpc.newBlockingStub(channel);
        authorStubAsync = AuthorServiceGrpc.newStub(channel);

        bookStub = BookServiceGrpc.newBlockingStub(channel);
        bookStubAsync = BookServiceGrpc.newStub(channel);

        System.out.println("[CLIENT] Initializing client...");
    }

    private BookOuterClass.Book createBook(BookOuterClass.Book book) {
        System.out.println("[CLIENT creating Book]" + book);
        return bookStub.createBook(book);
    }

    private AuthorOuterClass.Author createAuthor(AuthorOuterClass.Author author) {
        System.out.println("[CLIENT] creating Author" + author);

        return authorStub.createAuthor(author);
    }

    private List<AuthorOuterClass.Author> getAllAuthors() {
        List<AuthorOuterClass.Author> authorList = new ArrayList<>();

        authorStub.getAllAuthors(Empty.newBuilder().build())
        .forEachRemaining(author -> authorList.add(author));
        return authorList;
    }

    private List<BookOuterClass.Book> getAllBooks() {
        List<BookOuterClass.Book> bookList = new ArrayList<>();

        bookStub.getAllBooks(Empty.newBuilder().build())
                .forEachRemaining(book -> bookList.add(book));
        return bookList;
    }

    public static void main(String[] args) {
        BookstoreClient client = new BookstoreClient();
        client.init();

        AuthorOuterClass.Author a1 = AuthorOuterClass.Author.newBuilder()
                .setFirstName("R.L")
                .setLastName("Stine")
                .setAge(77)
                .setId(1)
                .build();

        AuthorOuterClass.Author a2 = AuthorOuterClass.Author.newBuilder()
                .setFirstName("Stephen")
                .setLastName("King")
                .setAge(73)
                .setId(2)
                .build();

        AuthorOuterClass.Author a3 = AuthorOuterClass.Author.newBuilder()
                .setFirstName("J.K.")
                .setLastName("Rowling")
                .setAge(55)
                .setId(3)
                .build();

        AuthorOuterClass.Author a4 = AuthorOuterClass.Author.newBuilder()
                .setFirstName("Lemony")
                .setLastName("Snicket")
                .setAge(50)
                .setId(4)
                .build();

        client.createAuthor(a1);
        client.createAuthor(a2);
        client.createAuthor(a3);
        client.createAuthor(a4);

        BookOuterClass.Book b1 = BookOuterClass.Book.newBuilder()
                .setName("Welcome to Dead House")
                .addAuthors(a1)
                .setYearPublished(1992)
                .setPublisher("Scholastic")
                .setPrice(17.80f)
                .build();

        BookOuterClass.Book b2 = BookOuterClass.Book.newBuilder()
                .setName("Carrie")
                .addAuthors( a2)
                .setYearPublished(1974)
                .setPublisher("Scribner")
                .setPrice(6.99f)
                .build();

        BookOuterClass.Book b3 = BookOuterClass.Book.newBuilder()
                .setName("Harry Potter and the Sorcerer's Stone")
                .addAuthors(a3)
                .setYearPublished(1997)
                .setPublisher("Scholastic")
                .setPrice(6.98f)
                .build();

        BookOuterClass.Book b4= BookOuterClass.Book.newBuilder()
                .setName("A Series of Unfortunate Events: The Bad Beginning")
                .addAuthors(a4)
                .setYearPublished(1999)
                .setPublisher("Harper Collins")
                .setPrice(7.99f)
                .build();

        client.createBook(b1);
        client.createBook(b2);
        client.createBook(b3);
        client.createBook(b4);

        System.out.println("[CLIENT] Fetch all Authors");
        client.getAllAuthors().forEach(author -> System.out.println(author));

        System.out.println("[CLIENT] Fetch all Books");
        client.getAllBooks().forEach(book -> System.out.println(book));
    }


}
