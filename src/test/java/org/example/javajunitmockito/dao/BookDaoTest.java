package org.example.javajunitmockito.dao;

import org.example.javajunitmockito.model.Book;
import org.example.javajunitmockito.model.BookBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

abstract class BookDaoTest {

    protected static final String ISBN_NOT_PRESENT = "random";
    private BookDao daoUnderTest;
    protected List<Book> dummyBooks;

    protected abstract BookDao getDaoToTest();

    @BeforeEach
    void setUp() {
        this.daoUnderTest = requireNonNull(getDaoToTest());
        this.dummyBooks = getDummyBooks();
        boolean allSaved = this.dummyBooks
                .stream()
                .map(daoUnderTest::saveBook)
                .allMatch(Boolean::valueOf);

        assertTrue(allSaved);
    }

    @AfterEach
    void tearDown() {
        boolean allDeleted = dummyBooks
                .stream()
                .map(Book::getISBN)
                .map(daoUnderTest::deleteBookByISBN)
                .allMatch(Objects::nonNull);

        assertTrue(allDeleted);

    }

    protected List<Book> getDummyBooks() {
        List<Book> dummyBooks = new LinkedList<>();
        String titlePrefix = "Book";
        String authorPrefix = "Author";
        String publisherPrefix = "Publisher";
        String categoryPrefix = "Category";

        for (int i = 0; i < 3; i++) {
            String randomId = UUID.randomUUID().toString();
            Book book = BookBuilder
                    .builder()
                    .withISBN(randomId)
                    .withTitle(titlePrefix.concat(randomId))
                    .withAuthor(authorPrefix.concat(randomId))
                    .withPublisher(publisherPrefix.concat(randomId))
                    .withCategory(categoryPrefix.concat(randomId))
                    .build();
            dummyBooks.add(book);
        }
        return dummyBooks;
    }

    @Test
    void testSaveBook() {
        Book book = BookBuilder
                .builder()
                .withISBN(ISBN_NOT_PRESENT)
                .withTitle("Title")
                .withPublisher("Publisher")
                .withAuthor("Author")
                .withCategory("Category")
                .build();

        assertTrue(daoUnderTest.saveBook(book));
        this.dummyBooks.add(book);

        Optional<Book> shouldBeSavedBook = daoUnderTest.getBookByISBN(ISBN_NOT_PRESENT);

        assertTrue(shouldBeSavedBook.isPresent());
        Book savedBook = assertDoesNotThrow(() -> shouldBeSavedBook.get());

        assertEquals(book.getISBN(), savedBook.getISBN());
    }

    @Test
    void testGetBookByISBN() {
        Book book = dummyBooks.get(0);
        Optional<Book> mightHaveBook = daoUnderTest.getBookByISBN(book.getISBN());

        assertTrue(mightHaveBook.isPresent());
        Book savedBook = mightHaveBook.get();

        assertBookEquals(book, savedBook);

        Optional<Book> shouldNotContainBook = daoUnderTest.getBookByISBN(ISBN_NOT_PRESENT);

        assertFalse(shouldNotContainBook.isPresent());
    }

    @Test
    void testUpdateBook() {
        Book book = dummyBooks.get(0);
        BookBuilder bookBuilder = BookBuilder
                .builder()
                .withISBN(book.getISBN())
                .withTitle("Updated Title")
                .withAuthor("Updated Author")
                .withPublisher("Updated Publisher")
                .withCategory("Updated Category");

        Book bookToUpdate = bookBuilder.build();
        Book previousBook = daoUnderTest.updateBook(bookToUpdate);
        assertNotNull(previousBook);

        Optional<Book> shouldHaveUpdatedBook = daoUnderTest.getBookByISBN(bookToUpdate.getISBN());
        assertTrue(shouldHaveUpdatedBook.isPresent());

        Book updatedBook = assertDoesNotThrow(shouldHaveUpdatedBook::get);
        assertBookEquals(bookToUpdate, updatedBook);

        assertNull(daoUnderTest.updateBook(bookBuilder
                .withISBN("invalid")
                .build()));
    }

    @Test
    void testDeleteBook() {
        Book bookToDelete = dummyBooks.get(0);

        Book deletedBook = daoUnderTest.deleteBookByISBN(bookToDelete.getISBN());
        assertNotNull(deletedBook);

        assertBookEquals(bookToDelete, deletedBook);

        dummyBooks.remove(0);

        assertNull(daoUnderTest.deleteBookByISBN(ISBN_NOT_PRESENT));
    }

    private void assertBookEquals(Book book, Book savedBook) {
        assertEquals(book.getISBN(), savedBook.getISBN());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertTrue(book.getAuthors().containsAll(savedBook.getAuthors()));
        assertEquals(book.getPublisher(), savedBook.getPublisher());
        assertEquals(book.getCategory(), savedBook.getCategory());
    }
}