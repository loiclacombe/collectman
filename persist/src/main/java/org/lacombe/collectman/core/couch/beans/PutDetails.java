package org.lacombe.collectman.core.couch.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Damaki
 * Date: 02/03/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class PutDetails {
    private boolean ok;
    private String id;
    private String rev;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    @Override
    public String toString() {
        return "PutDetails{" +
                "id='" + id + '\'' +
                ", ok=" + ok +
                ", rev='" + rev + '\'' +
                '}';
    }
}
