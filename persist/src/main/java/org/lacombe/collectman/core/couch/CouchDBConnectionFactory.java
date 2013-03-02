package org.lacombe.collectman.core.couch;

import java.net.URI;


/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 03:18
 * To change this template use File | Settings | File Templates.
 */

public interface CouchDBConnectionFactory {
    DocumentConnection create(URI serverUri);
}
