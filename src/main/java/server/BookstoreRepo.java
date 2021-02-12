package server;

import models.AuthorOuterClass;
import models.BookOuterClass;

import java.util.ArrayList;
import java.util.List;

public class BookstoreRepo {

    private List<AuthorOuterClass.Author> authorList = new ArrayList<>();
    private List<BookOuterClass.Book> bookList = new ArrayList<>();

    private static BookstoreRepo thisRepo;

    private BookstoreRepo() {
    }

    public static BookstoreRepo getInstance() {
        if(thisRepo == null)
            thisRepo = new BookstoreRepo();

        return thisRepo;
    }

    public AuthorOuterClass.Author createAuthor(AuthorOuterClass.Author author) {
        authorList.add(author);
        return author;
    }

    public List<AuthorOuterClass.Author> getAllAuthors() {
        return authorList;
    }

    public BookOuterClass.Book createBook(BookOuterClass.Book book) {
        bookList.add(book);
        return book;
    }

    public List<BookOuterClass.Book> getAllBooks() {
        return bookList;
    }

    public AuthorOuterClass.Author getAuthor(int id) {
        for(AuthorOuterClass.Author author: authorList) {
            if(author.getId() == id) return author;
        }
        return null;
    }

    public BookOuterClass.Book getBook(int id) {
        for(BookOuterClass.Book book: bookList) {
            if(book.getId() == id) return book;
        }
        return null;
    }
}
