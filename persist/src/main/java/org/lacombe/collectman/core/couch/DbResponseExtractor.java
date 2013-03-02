package org.lacombe.collectman.core.couch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.lacombe.collectman.core.couch.beans.PutDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 02:35
 * To change this template use File | Settings | File Templates.
 */

public class DbResponseExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(DbResponseExtractor.class);

    public static final String UUIDS_TAG = "uuids";
    private Gson gson;

    private static final Type UUID_CONTENT_TYPE = new TypeToken<HashMap<String, ArrayList<String>>>() {
    }.getType();

    @Inject
    public DbResponseExtractor(Gson gson) {
        this.gson = gson;
    }

    String extractUuidFrom(HttpResponse response) throws IOException {
        StatusUtils.checkStatusOk(response);

        String jsonResponse = EntityUtils.toString(response.getEntity());
        LOG.trace("jsonResponse:{}", jsonResponse);
        HashMap<String, ArrayList<String>> content = gson.fromJson(jsonResponse, UUID_CONTENT_TYPE);

        return content.get(UUIDS_TAG).get(0);
    }

    String extractTextFrom(HttpResponse response) throws IOException {
        StatusUtils.checkStatusOk(response);

        String text = EntityUtils.toString(response.getEntity());
        LOG.trace("text:{}", text);

        return text;
    }

    PutDetails extractPutDetailsFrom(HttpResponse response) throws IOException {
        String jsonResponse = EntityUtils.toString(response.getEntity());
        LOG.trace("jsonResponse:{}", jsonResponse);
        PutDetails putDetails = gson.fromJson(jsonResponse, PutDetails.class);

        return putDetails;
    }
}
