package org.example.javajunitmockito.mocking;

import org.example.javajunitmockito.exception.BookNotFoundException;
import org.example.javajunitmockito.model.Book;
import org.example.javajunitmockito.model.BookBuilder;
import org.example.javajunitmockito.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

public class MockitoDoMethodsTest {

    @Mock
    private BookService bookDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDoThrow() {
        Mockito.doThrow(BookNotFoundException.class)
                .when(bookDao)
                .getBook(anyString());

        for (int i = 0; i < 5; i++) {
            String randomId = UUID.randomUUID().toString();
            assertThrows(BookNotFoundException.class, () ->
                    bookDao.getBook(randomId));
        }
    }

    @Test
    void testDoReturn() {

        Book book = BookBuilder
                .builder()
                .withAuthor("Random Author")
                .build();

        Mockito.doReturn(book)
                .when(bookDao)
                .getBook(anyString());


        for (int i = 0; i < 5; i++) {
            String randomUUID = UUID.randomUUID().toString();
            Book returnedBook = bookDao.getBook(randomUUID);
            assertEquals(book, returnedBook);
        }

    }

    @Test
    void testDoAnswer() {
        Mockito.doAnswer(answer -> {
            String ISBN = (String) answer.getArgument(0);
            return BookBuilder
                    .builder()
                    .withISBN(ISBN)
                    .build();
        }).when(bookDao).getBook(anyString());

        for (int i = 0; i < 5; i++) {
            String randomISBN = UUID.randomUUID().toString();
            Book book = bookDao.getBook(randomISBN);
            assertEquals(randomISBN, book.getISBN());
        }
    }

}
