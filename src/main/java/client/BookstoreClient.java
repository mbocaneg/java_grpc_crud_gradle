package client;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import models.AuthorOuterClass;
import services.AuthorServiceGrpc;

import java.util.ArrayList;
import java.util.List;

public class BookstoreClient {
    ManagedChannel channel;
    AuthorServiceGrpc.AuthorServiceBlockingStub authorStub;
    AuthorServiceGrpc.AuthorServiceStub authorStubAsync;

    private void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 1234)
                .usePlaintext()
                .build();

        authorStub = AuthorServiceGrpc.newBlockingStub(channel);
        authorStubAsync = AuthorServiceGrpc.newStub(channel);

        System.out.println("[CLIENT] Initializing client...");
    }

    private AuthorOuterClass.Author createAuthor(AuthorOuterClass.Author author) {
        System.out.println("[SERVER] creating Author" + author);

        return authorStub.createAuthor(author);
    }

    private List<AuthorOuterClass.Author> getAllAuthors() {
        List<AuthorOuterClass.Author> authorList = new ArrayList<>();

        authorStub.getAllAuthors(Empty.newBuilder().build())
        .forEachRemaining(author -> authorList.add(author));
        return authorList;
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

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 1234)
                .usePlaintext()
                .build();

        client.createAuthor(a1);
        client.createAuthor(a2);
        client.createAuthor(a3);
        client.createAuthor(a4);


        System.out.println("[SERVER] Fetch all Authors");
        client.getAllAuthors().forEach(author -> System.out.println(author));
    }
}
