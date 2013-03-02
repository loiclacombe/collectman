package org.lacombe.collectman.core.service;

import com.google.inject.Inject;
import org.lacombe.collectman.beans.Book;
import org.lacombe.collectman.beans.BookCollection;
import org.lacombe.collectman.core.couch.DocumentConnection;
import org.lacombe.collectman.core.couch.beans.PutDetails;
import org.lacombe.collectman.inject.Collectman;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class CollectionService implements AutoCloseable {

    private DocumentConnection documentConnection;

    @Inject
    public CollectionService(DocumentConnection documentConnection) {
        this.documentConnection = documentConnection;

        open();
    }

    private void open() {
        try {
            if (!documentConnection.documentExists()) {
                documentConnection.createDatabase();
            }
        } catch (URISyntaxException | IOException e) {
            throw new ServiceException(e);
        }
    }

    public void close() {

    }

    public void deleteStorage() {
        documentConnection.deleteDatabase();
    }

    public PutDetails put(Book book) {
        BookCollection books = new BookCollection();
        books.getBooks().add(book);

        try {
            return documentConnection.putObject(books);
        } catch (URISyntaxException | IOException e) {
            throw new ServiceException(e);
        }
    }

    public BookCollection getContent() {
        return documentConnection.readContent(BookCollection.class);
    }
}
