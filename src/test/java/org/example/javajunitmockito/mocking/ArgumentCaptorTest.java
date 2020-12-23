package org.example.javajunitmockito.mocking;

import org.example.javajunitmockito.dao.BookDao;
import org.example.javajunitmockito.exception.BookNotFoundException;
import org.example.javajunitmockito.service.BookService;
import org.example.javajunitmockito.service.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ArgumentCaptorTest {

    @Test
    void testArgumentPassedToDaoIsSameWithArgumentCaptor() {
        BookDao mockBookDao = Mockito.mock(BookDao.class);
        BookService service = new BookServiceImpl(mockBookDao);

        String ISBN_Passed = "123";
        try {
            service.getBook(ISBN_Passed);
        } catch (BookNotFoundException e) {}

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(mockBookDao)
                .getBookByISBN(argumentCaptor.capture());

        Assertions.assertEquals(ISBN_Passed, argumentCaptor.getValue());
    }
}
