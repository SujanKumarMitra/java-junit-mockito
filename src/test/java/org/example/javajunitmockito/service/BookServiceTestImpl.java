package org.example.javajunitmockito.service;

public class BookServiceTestImpl extends BookServiceTest {
    @Override
    public BookService getBookServiceToTest() {
        return new BookServiceImpl();
    }
}
