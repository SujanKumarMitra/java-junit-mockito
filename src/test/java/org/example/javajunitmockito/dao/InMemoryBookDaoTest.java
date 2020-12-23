package org.example.javajunitmockito.dao;

public class InMemoryBookDaoTest extends BookDaoTest {

    private BookDao bookDao;

    public InMemoryBookDaoTest() {
        bookDao = new InMemoryBookDao();
    }

    @Override
    public BookDao getDaoToTest() {
        return bookDao;
    }
}
