package org.lacombe.collectman.core.couch;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.lacombe.collectman.inject.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class CouchModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HttpClient.class).to(DefaultHttpClient.class);
    }

    @Provides
    public Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
        //     gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

    @Provides
    @Path
    public Joiner createJoiner() {
        return Joiner.on('/');
    }
}
