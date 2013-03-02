package org.lacombe.collectman.core.couch;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.lacombe.collectman.beans.BookCollection;
import org.lacombe.collectman.core.couch.beans.PutDetails;
import org.lacombe.collectman.inject.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.lacombe.collectman.inject.CouchKeys.*;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 01:28
 * To change this template use File | Settings | File Templates.
 */
public class DocumentConnection {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentConnection.class);

    private HttpClient httpclient;
    private Gson gson;
    private DbResponseExtractor dbResponseExtractor;
    private final Joiner pathJoiner;
    private String dbName;

    private String uuid;
    private final Provider<URIBuilder> uriBuilderProvider;


    @Inject
    public DocumentConnection(Gson gson, HttpClient httpclient, Provider<URIBuilder> uriBuilderProvider,
                              DbResponseExtractor dbResponseExtractor, @Path Joiner pathJoiner, @Named(COUCH_DB_NAME) String dbName) {
        this.gson = gson;
        this.httpclient = httpclient;
        this.uriBuilderProvider = uriBuilderProvider;
        this.dbResponseExtractor = dbResponseExtractor;
        this.pathJoiner = pathJoiner;
        this.dbName=dbName;
    }

    public String nextUUID()  {
        try {
        URIBuilder builder = uriBuilderProvider.get();
        builder.setPath("/_uuids");

        HttpGet httpGet = new HttpGet(builder.build());
        HttpResponse response = httpclient.execute(httpGet);

        return dbResponseExtractor.extractUuidFrom(response);
    } catch (URISyntaxException | IOException e) {

        Throwables.propagate(e);
    }
    throw new IllegalStateException("unreachable code");
    }

    public boolean documentExists() {
        try {
            URIBuilder builder = uriBuilderProvider.get();
            builder.setPath(pathJoiner.join("", dbName));
            URI uri = builder.build();
            HttpGet get = new HttpGet(uri);
            HttpResponse response = httpclient.execute(get);
            get.releaseConnection();
            return StatusUtils.checkHttpStatusIsOkIn(response);
        } catch (URISyntaxException | IOException e) {

            Throwables.propagate(e);
        }
        throw new IllegalStateException("unreachable code");
    }

    public void createDatabase() throws URISyntaxException, IOException {
        uuid = nextUUID();
        putJson(pathJoiner.join("", dbName), uuid);
    }

    private PutDetails putJson(String path, String json) throws URISyntaxException, IOException {
        checkNotNull("json is null", json);
        LOG.trace("Connecting to {}", path);

        URIBuilder builder = uriBuilderProvider.get();
        builder.setPath(path);
        URI uri = builder.build();
        LOG.debug("uri:{}", uri);
        HttpPut httpPut = new HttpPut(uri);

        httpPut.setEntity(new StringEntity(json));
        HttpResponse response = httpclient.execute(httpPut);

        StatusUtils.checkHttpStatusIsCreatedIn(response);
        PutDetails putDetails = dbResponseExtractor.extractPutDetailsFrom(response);

        return putDetails;
    }

    public <T> T readContent(Class<T> clazz) {
        try {
            URIBuilder builder = uriBuilderProvider.get();
            URI uri = builder.setPath(contentPath()).build();
            HttpGet get = new HttpGet(uri);
            HttpResponse response = httpclient.execute(get);
            return gson.fromJson(dbResponseExtractor.extractTextFrom(response), clazz);
        } catch (URISyntaxException | IOException e) {

            Throwables.propagate(e);
        }
        throw new RuntimeException();
    }

    private String contentPath() {
        return pathJoiner.join("", dbName, uuid);
    }

    public void deleteDatabase() {
        String path = pathJoiner.join("", dbName);
        LOG.info("deleting database {}", path);
        try {
            HttpDelete delete = new HttpDelete(uriBuilderProvider.get().setPath(path).build());
            HttpResponse response = httpclient.execute(delete);
            StatusUtils.checkHttpStatusIsOkIn(response);
            delete.releaseConnection();
        } catch (URISyntaxException | IOException e) {

            Throwables.propagate(e);
        }
    }

    public PutDetails putObject(BookCollection bookCollection) throws IOException, URISyntaxException {
        checkNotNull("bookCollection is null", bookCollection);
        return putJson(contentPath(), gson.toJson(bookCollection));

    }
}
