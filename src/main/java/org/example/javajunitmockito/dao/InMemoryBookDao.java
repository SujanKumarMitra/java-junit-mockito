package org.example.javajunitmockito.dao;

import org.example.javajunitmockito.model.Book;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBookDao implements BookDao {

    Map<String, Book> bookMap;

    public InMemoryBookDao() {
        this(new LinkedHashMap<>());
    }

    public InMemoryBookDao(Map<String, Book> bookMap) {
        this.bookMap = bookMap;
    }

    @Override
    public boolean saveBook(Book book) {
        Book returnedBook = bookMap.putIfAbsent(book.getISBN(), book);
        return returnedBook == null;
    }

    @Override
    public Optional<Book> getBookByISBN(String ISBN) {
        Book book = bookMap.get(ISBN);
        return book == null ? Optional.empty() : Optional.of(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookMap.replace(book.getISBN(), book);
    }

    @Override
    public Book deleteBookByISBN(String ISBN) {
        return bookMap.remove(ISBN);
    }
}
