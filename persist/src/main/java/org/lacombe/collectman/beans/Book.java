package org.lacombe.collectman.beans;


import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 01:02
 * To change this template use File | Settings | File Templates.
 */
public class Book {
    @SerializedName("_id")
    private String id;

    private DateTime dateCreated;

    private String title;

    private List<String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (dateCreated != null ? !dateCreated.equals(book.dateCreated) : book.dateCreated != null) return false;
        if (tags != null ? !tags.equals(book.tags) : book.tags != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateCreated != null ? dateCreated.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "dateCreated=" + dateCreated +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                '}';
    }
}
