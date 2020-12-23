package org.example.javajunitmockito.mocking;

import org.example.javajunitmockito.dao.BookDao;
import org.example.javajunitmockito.model.Book;
import org.example.javajunitmockito.model.BookBuilder;
import org.example.javajunitmockito.service.BookService;
import org.example.javajunitmockito.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MockAnnotationTest {

    @Mock // mock this bean
    private BookDao mockBookDao;

    @InjectMocks // inject mock dependencies
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDaoDependencyIsInjected() {
        assertNotNull(bookServiceImpl);
        assertNotNull(mockBookDao);
    }

    @Test
    void testMockIsInvoked() {

        Book sampleBook = BookBuilder
                .builder()
                .withISBN("ISBN")
                .withAuthor("Author")
                .withPublisher("Publisher")
                .withCategory("Category")
                .build();

        Mockito.when(mockBookDao.saveBook(sampleBook))
                .thenReturn(true);

        Book savedBook = bookServiceImpl.addBook(sampleBook);

        assertEquals(sampleBook,savedBook);
    }
}
