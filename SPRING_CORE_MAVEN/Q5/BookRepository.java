package com.library.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    public void saveBook(String bookName) {
        System.out.println("Book '" + bookName + "' has been saved to the repository.");
    }
}
