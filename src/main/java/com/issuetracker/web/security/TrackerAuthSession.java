package com.issuetracker.web.security;

import com.issuetracker.dao.api.UserDao;
import com.issuetracker.model.User;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class TrackerAuthSession extends AuthenticatedWebSession {

    @Inject
    private UserDao userDao;
    private User user;
    private TrackerSettings settings = new TrackerSettings();

    public TrackerAuthSession(Request request) {
        super(request);
    }

    @Override
    public void signOut() {
        user = null;
        super.signOut();
    }

    public TrackerSettings getSettings() {
        return settings;
    }

    /**
     * @returns currently logged user (full object), or null.
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public boolean authenticate(String name, String pass) {
        if (this.user != null) {
            return true;
        }
        return authenticate(new User(name, pass));
    }

    public boolean authenticate(User user_) {
        if (this.user != null) {
            return true;
        }

        try {
            this.user = userDao.loadUserIfPasswordMatches(user_.getName(), user_.getPassword());
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Roles getRoles() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
