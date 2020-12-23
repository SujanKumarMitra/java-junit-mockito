package org.example.javajunitmockito.service;

import org.example.javajunitmockito.dao.BookDao;
import org.example.javajunitmockito.exception.BookAlreadyExistsException;
import org.example.javajunitmockito.exception.BookNotFoundException;
import org.example.javajunitmockito.model.Book;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

public class BookServiceImpl implements BookService {

    protected BookDao bookDao;

    public BookServiceImpl() {
    }

    public BookServiceImpl(BookDao bookDao) {
        Objects.requireNonNull(bookDao);
        this.bookDao = bookDao;
    }

    @Override
    public void setBookDao(BookDao bookDao) {
        Objects.requireNonNull(bookDao);
        this.bookDao = bookDao;
    }

    @Override
    public Book addBook(Book book) throws BookAlreadyExistsException {
        boolean result = bookDao.saveBook(book);
        if (!result)
            throw new BookAlreadyExistsException(
                    format("book already exists with isbn [{0}]"
                            , book.getISBN())
            );
        return book;
    }

    @Override
    public Book getBook(String ISBN) throws BookNotFoundException {
        Optional<Book> mightHaveBook = bookDao.getBookByISBN(ISBN);
        return mightHaveBook.orElseThrow(() -> getBookNotFoundException(ISBN));
    }

    @Override
    public Book updateBook(Book book) throws BookNotFoundException {
        Book previousBook = bookDao.updateBook(book);
        if (previousBook == null)
            throw getBookNotFoundException(book.getISBN());
        return previousBook;
    }

    @Override
    public Book deleteBook(String ISBN) throws BookNotFoundException {
        Book deletedBook = bookDao.deleteBookByISBN(ISBN);
        if (deletedBook == null)
            throw getBookNotFoundException(ISBN);
        return deletedBook;
    }

    private BookNotFoundException getBookNotFoundException(String ISBN) {
        return new BookNotFoundException(
                format("no book found with isbn [{0}]", ISBN));
    }
}
