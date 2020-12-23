package org.example.javajunitmockito.service;

import org.example.javajunitmockito.dao.BookDao;
import org.example.javajunitmockito.exception.BookAlreadyExistsException;
import org.example.javajunitmockito.exception.BookNotFoundException;
import org.example.javajunitmockito.model.Book;
import org.example.javajunitmockito.model.BookBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

abstract class BookServiceTest {

    protected abstract BookService getBookServiceToTest();

    @Mock
    private BookDao dao;
    private BookService serviceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.serviceUnderTest = getBookServiceToTest();
        serviceUnderTest.setBookDao(dao);
    }

    @Test
    void testAddValidBook() {
        Mockito.when(dao.saveBook(any()))
                .thenReturn(true);

        Book book = getRandomBookBuilder().build();

        Book savedBook = serviceUnderTest.addBook(book);
        assertNotNull(savedBook);
        assertEquals(book.getISBN(), savedBook.getISBN());

        Mockito.when(dao.saveBook(savedBook))
                .thenReturn(false);

        assertThrows(BookAlreadyExistsException.class, () ->
                serviceUnderTest.addBook(savedBook));
    }

    @Test
    void getBook() {
        Book randomBook = getRandomBookBuilder().build();
        String bookISBN = randomBook.getISBN();
        Mockito.doAnswer(answer -> {
            String ISBN = answer.getArgument(0);
            return ISBN.equals(bookISBN) ? Optional.of(randomBook) : Optional.empty();
        }).when(dao).getBookByISBN(anyString());

        Optional<Book> shouldHaveBook = dao.getBookByISBN(bookISBN);
        assertTrue(shouldHaveBook.isPresent());

        Book savedBook = shouldHaveBook.get();
        assertEquals(savedBook.getISBN(), bookISBN);

        assertFalse(dao.getBookByISBN("invalid").isPresent());
    }

    @Test
    void testUpdateBook() {
        String invalidISBN_ToUpdate = "invalid";
        Mockito.doThrow(new BookNotFoundException(""))
                .when(dao)
                .updateBook(argThat(matcher -> matcher.getISBN().equals(invalidISBN_ToUpdate)));

        Book invalidBookUpdate = getRandomBookBuilder()
                .withISBN(invalidISBN_ToUpdate)
                .build();
        assertThrows(BookNotFoundException.class, () -> serviceUnderTest.updateBook(invalidBookUpdate));

        String validISBN = "valid";
        Book bookBeforeUpdate = getRandomBookBuilder()
                .withISBN(validISBN)
                .withTitle("old title")
                .build();
        Book bookAfterUpdate = getRandomBookBuilder()
                .withISBN(validISBN)
                .withTitle("updated title")
                .build();
        Mockito.doReturn(bookBeforeUpdate)
                .when(dao)
                .updateBook(argThat(matcher -> matcher.getISBN().equals(validISBN)));

        Book bookBeforeUpdate1 = serviceUnderTest.updateBook(bookAfterUpdate);
        assertEquals(bookBeforeUpdate.getTitle(),bookBeforeUpdate1.getTitle());
    }

    @Test
    void testDeleteBook() {

        Book randomBook = getRandomBookBuilder().build();
        String bookISBN = randomBook.getISBN();

        Mockito.doAnswer(answer -> {
            String ISBN = answer.getArgument(0);
            return ISBN.equals(bookISBN) ? randomBook : null;
        }).when(dao).deleteBookByISBN(anyString());

        Book deletedBook = assertDoesNotThrow(() -> serviceUnderTest.deleteBook(bookISBN));
        assertEquals(bookISBN, deletedBook.getISBN());

        assertThrows(BookNotFoundException.class, () -> serviceUnderTest.deleteBook("invalidISBN"));

    }

    private BookBuilder getRandomBookBuilder() {
        return BookBuilder.builder()
                .withISBN("ISBN")
                .withAuthor("Author")
                .withTitle("Title")
                .withPublisher("Publisher")
                .withCategory("Category");
    }
}