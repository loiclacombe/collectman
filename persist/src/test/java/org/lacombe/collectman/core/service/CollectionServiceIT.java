package org.lacombe.collectman.core.service;

import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lacombe.collectman.beans.Book;
import org.lacombe.collectman.beans.BookCollection;
import org.lacombe.collectman.core.couch.CouchModule;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import static com.google.inject.Guice.createInjector;
import static org.junit.Assert.assertEquals;
import static org.lacombe.collectman.inject.CouchKeys.*;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 01:40
 * To change this template use File | Settings | File Templates.
 */
public class CollectionServiceIT {

    private URI serverUri = URI.create("http://test:test@tsl-arch:5984/");

    private CollectionService collectionService;

    private Module module = new AbstractModule() {

        @Override
        protected void configure() {
            install(new CouchModule());

            Properties properties = loadTestConfiguration();

            Names.bindProperties(super.binder(), properties);
        }

        @Provides
        public URIBuilder createUriBuilder(@Named(COUCH_DB_URI) String serverUri,
                                           @Named(COUCH_DB_USER) String user,
                                           @Named(COUCH_DB_PASSWORD) String password) throws URISyntaxException {
            return new URIBuilder(serverUri).setUserInfo(user, password);
        }
    };

    private Properties loadTestConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/collectman-it.properties"));
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return properties;
    }


    @Before
    public void setUp() {
        Injector injector = createInjector(module);
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");

        initDb(injector);
        collectionService = injector.getInstance(CollectionService.class);
    }

    private void initDb(Injector injector) {
        collectionService = injector.getInstance(CollectionService.class);

        try {
            collectionService.deleteStorage();
        } catch (ServiceException e) {
            //ignore
        }
    }

    @After
    public void tearDown() {
        collectionService.close();
    }


    /**
     * Initialize the database<br/>
     * Add a book<br/>
     * Retrieve the book<br/>
     * Then delete the database
     *
     * @throws Exception
     */
    @Test
    public void testPut() throws Exception {
        Book book = new Book();
        book.setTitle("Do android dream of electric sheeps?");
        book.setDateCreated(new DateTime());

        System.out.println(collectionService.put(book));
        BookCollection bookCollection = collectionService.getContent();
        System.out.println(bookCollection);

        assertEquals(book, bookCollection.getBooks().iterator().next());

        //collectionService.deleteStorage();
    }
}
