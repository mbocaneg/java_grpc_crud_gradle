package server;

import models.AuthorOuterClass;
import models.BookOuterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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

    public List<BookOuterClass.Book> getBookByAuthor(int id) {
        List<BookOuterClass.Book> bookByAuthor = bookList
                .stream()
                .filter(book -> book.getAuthorsList().stream()
                    .allMatch(author -> author.getId() == id)
                )
                .collect(Collectors.toList());
        return bookByAuthor;
    }

    public BookOuterClass.Book UpdateBook(BookOuterClass.Book bookToUpdate) {
        ListIterator<BookOuterClass.Book> iterator = bookList.listIterator();
        while (iterator.hasNext()) {
            BookOuterClass.Book book = iterator.next();
            if (book.getId() == bookToUpdate.getId()) {
                BookOuterClass.Book updatedBook = BookOuterClass.Book.newBuilder(bookToUpdate)
                        .build();
                iterator.set(updatedBook);
                return updatedBook;
            }
        }
        return null;
    }

    public void deleteBook(int id) {
        ListIterator<BookOuterClass.Book> iterator = bookList.listIterator();
        while (iterator.hasNext()) {
            BookOuterClass.Book book = iterator.next();
            if(book.getId() == id){
                iterator.remove();
                return;
            }
        }
    }

    public AuthorOuterClass.Author updateAuthor(AuthorOuterClass.Author authorToUpdate) {
        ListIterator<AuthorOuterClass.Author> iterator = authorList.listIterator();
        while (iterator.hasNext()) {
            AuthorOuterClass.Author author = iterator.next();
            if (author.getId() == authorToUpdate.getId()) {
                AuthorOuterClass.Author updatedAuthor = AuthorOuterClass.Author.newBuilder(author)
                        .build();
                iterator.set(updatedAuthor);
                return updatedAuthor;
            }
        }
        return null;
    }
}
