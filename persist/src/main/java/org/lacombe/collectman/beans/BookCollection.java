package org.lacombe.collectman.beans;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("_id")
    private String id;
    @SerializedName("_rev")
    private String rev;

    private Collection<Book> books=new ArrayList<>();

    private Collection<Author> authors=new ArrayList<>();

    public Collection<Book> getBooks() {
        return books;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }


    @Override
    public String toString() {
        return "BookCollection{" +
                "authors=" + authors +
                ", id='" + id + '\'' +
                ", rev='" + rev + '\'' +
                ", books=" + books +
                '}';
    }
}
