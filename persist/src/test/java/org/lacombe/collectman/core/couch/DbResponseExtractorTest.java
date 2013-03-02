package org.lacombe.collectman.core.couch;

import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.createControl;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 02:48
 * To change this template use File | Settings | File Templates.
 */
public class DbResponseExtractorTest {
    private IMocksControl control = createControl();
    private Gson gson = new Gson();
    private DbResponseExtractor uuidExtrator;

    @Before
    public void setUp() {
        control.resetToStrict();
        uuidExtrator = new DbResponseExtractor(gson);
    }

    @Test
    public void testExtractUuidFromResponse() throws Exception {

        BasicHttpResponse response = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK"));
        response.setEntity(new StringEntity("{\"uuids\":[\"a7036e8d41852efaa55aff34860374d6\"]}"));
        assertEquals("a7036e8d41852efaa55aff34860374d6", uuidExtrator.extractUuidFrom(response));
    }
}
