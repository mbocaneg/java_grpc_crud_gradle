package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class BookstoreServer {
    Server server;

    private void start() throws IOException, InterruptedException {
        server = ServerBuilder
                .forPort(1234)
                .addService(new AuthorServiceImpl())
                .build()
                .start();

        System.out.println("Starting Server");
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BookstoreServer s = new BookstoreServer();
        s.start();
    }
}
