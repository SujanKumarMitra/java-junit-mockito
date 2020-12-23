package org.example.javajunitmockito.mocking;

import org.example.javajunitmockito.dao.BookDao;
import org.example.javajunitmockito.exception.BookNotFoundException;
import org.example.javajunitmockito.service.BookService;
import org.example.javajunitmockito.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.argThat;

public class ArgumentMatchersTest {

    @Test
    void testDaoIsCalledByService() {
        BookDao mockBookDao = Mockito.mock(BookDao.class);
        BookService service = new BookServiceImpl(mockBookDao);

        try {
            service.getBook("123");
        } catch (BookNotFoundException e) {
        }

        Mockito.verify(mockBookDao)
                .getBookByISBN(ArgumentMatchers.anyString());
    }

    @Test
    void testArgumentPassedToDaoIsSameWithArgumentMatcher() {
        BookDao mockBookDao = Mockito.mock(BookDao.class);
        BookService service = new BookServiceImpl(mockBookDao);

        String ISBN_Passed = "123";
        try {
            service.getBook(ISBN_Passed);
        } catch (BookNotFoundException e) {}

        Mockito.verify(mockBookDao)
                .getBookByISBN(argThat(argPassed -> argPassed.equals(ISBN_Passed)));
    }

}
