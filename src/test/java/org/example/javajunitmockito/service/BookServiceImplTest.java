package org.example.javajunitmockito.service;

public class BookServiceImplTest extends BookServiceTest {

    private BookServiceImpl bookService;

    public BookServiceImplTest() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public BookService getBookServiceToTest() {
        return bookService;
    }
}
