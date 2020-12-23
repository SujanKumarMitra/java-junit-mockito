package org.example.javajunitmockito.model;

import java.util.List;

public interface Book {

    String getISBN();

    String getTitle();

    List<String> getAuthors();

    String getPublisher();

    String getCategory();

}
