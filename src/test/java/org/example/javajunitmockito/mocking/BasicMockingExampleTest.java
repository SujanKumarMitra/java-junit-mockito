package org.example.javajunitmockito.mocking;


import org.example.javajunitmockito.dao.BookDao;
import org.example.javajunitmockito.model.Book;
import org.example.javajunitmockito.model.BookBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BasicMockingExampleTest {

    @Test
    void testMockObjectShouldNotBeNull() {
        BookDao mockDao = Mockito.mock(BookDao.class);
        assertNotNull(mockDao);
        System.out.println(mockDao);
    }

    @Test
    void testMockObjectShouldBehaveAsExpected() {

        String ISBN = "123";
        Book builtBook = BookBuilder
                .builder()
                .withISBN(ISBN)
                .withTitle("Random Title")
                .withAuthor("Random Author")
                .withPublisher("Random Publisher")
                .withCategory("Random Category")
                .build();

        BookDao mockDao = Mockito.mock(BookDao.class);
        Mockito.when(mockDao.getBookByISBN(ISBN))
                .thenReturn(Optional.of(builtBook));

        Optional<Book> mightHaveBook = mockDao.getBookByISBN(ISBN);

        assertTrue(mightHaveBook.isPresent());
        Book returnedBook = assertDoesNotThrow(mightHaveBook::get);

        assertEquals(ISBN, returnedBook.getISBN());

        assertEquals(builtBook.getTitle(), returnedBook.getTitle());
        assertTrue(builtBook.getAuthors().containsAll(returnedBook.getAuthors()));
        assertEquals(builtBook.getPublisher(), returnedBook.getPublisher());
        assertEquals(builtBook.getCategory(), returnedBook.getCategory());
    }
}
