package org.lacombe.collectman.core.couch;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 02:58
 * To change this template use File | Settings | File Templates.
 */
public class StatusUtils {
    private static final Logger LOG = LoggerFactory.getLogger(StatusUtils.class);

    public static void checkStatusOk(HttpResponse response) {
        boolean statusOk = checkHttpStatusIsOkIn(response);
        checkArgument(statusOk, response.getStatusLine().getReasonPhrase());
    }

    public static boolean checkHttpStatusIsOkIn(HttpResponse response) {
        return checkHttpStatusIn(response, is(HttpStatus.SC_OK));
    }

    public static boolean checkHttpStatusIsCreatedIn(HttpResponse response) {
        return checkHttpStatusIn(response, is(HttpStatus.SC_CREATED));
    }

    private static int is(int code) {
        return code;
    }

    private static boolean checkHttpStatusIn(HttpResponse response, int acceptedStatus) {
        StatusLine statusLine = response.getStatusLine();
        LOG.trace("statusLine:{}", statusLine);
        return statusLine.getStatusCode() == acceptedStatus;
    }

}
