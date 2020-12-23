package org.example.javajunitmockito.model;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class BookBuilder {

    protected final BookImpl book;

    protected BookBuilder(BookImpl book) {
        this.book = book;
    }

    public static BookBuilder builder() {
        return new BookBuilder(new BookImpl());
    }

    public Book build() {
        return this.book;
    }

    public BookBuilder withISBN(String ISBN) {
        book.setISBN(ISBN);
        return this;
    }

    public BookBuilder withTitle(String title) {
        book.setTitle(title);
        return this;
    }

    public BookBuilder withAuthor(String author) {
        getBookAuthors().add("author");
        return this;
    }

    public BookBuilder withAuthors(String ...authors) {
        getBookAuthors().addAll(asList(authors));
        return this;
    }

    public BookBuilder withPublisher(String publisher) {
        book.setPublisher(publisher);
        return this;
    }

    public BookBuilder withCategory(String category) {
        book.setCategory(category);
        return this;
    }


    private List<String> getBookAuthors() {
        List<String> authors = book.getAuthors();
        if(authors == null) {
            authors = new LinkedList<>();
            book.setAuthors(authors);
        }
        return authors;
    }


}
