package be.fp.distriWebApp.webapp.action;

import java.io.Serializable;
import java.util.List;

import org.appfuse.dao.SearchException;

public class UserList extends BasePage implements Serializable {
    private static final long serialVersionUID = 972359310602744018L;

    private String query;

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public UserList() {
        setSortColumn("username");
    }

    public List getUsers() {
        try {
            return sort(userManager.search(query));
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(userManager.search(query));
        }
    }

    public String search() {
        return "success";
    }
}
