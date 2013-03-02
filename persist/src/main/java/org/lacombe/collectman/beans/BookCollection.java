package org.lacombe.collectman.beans;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 02:00
 * To change this template use File | Settings | File Templates.
 */
public class BookCollection {
    private Collection<Book> books=new ArrayList<>();

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookCollection{" +
                "books=" + books +
                '}';
    }
}
