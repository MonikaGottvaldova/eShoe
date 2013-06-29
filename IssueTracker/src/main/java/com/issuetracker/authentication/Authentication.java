/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.issuetracker.authentication;

import org.picketbox.core.DefaultPicketBoxManager;
import org.picketbox.core.PicketBoxManager;
import org.picketbox.core.config.ConfigurationBuilder;
import org.picketbox.core.config.PicketBoxConfiguration;
import org.picketbox.core.UserContext;
import org.picketbox.core.authentication.AuthenticationMechanism;
import org.picketbox.core.authentication.credential.UsernamePasswordCredential;
import org.picketbox.core.exceptions.AuthenticationException;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.Group;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.SimpleGroup;
import org.picketlink.idm.model.SimpleRole;
import org.picketlink.idm.credential.PlainTextPassword;
import org.picketlink.idm.model.SimpleUser;

/**
 *
 * @author Monika
 */
public class Authentication {

    private PicketBoxManager picketBoxManager;

//    @Before
    public void onSetup() throws AuthenticationException {
        createPicketBoxManager();
        populateIdentityStore();
    }

    /**
     * Populates the identity store with user information.
     */
    private void populateIdentityStore() {
        IdentityManager identityManager = picketBoxManager.getIdentityManager();
        SimpleUser adminUser = new SimpleUser("admin");
        adminUser.setEmail("admin@picketbox.com");
        adminUser.setFirstName("The");
        adminUser.setLastName("Admin");
        identityManager.add(adminUser);
        identityManager.updateCredential(adminUser, new PlainTextPassword("admin".toCharArray()));
        Role developerRole = new SimpleRole("developer");
        identityManager.add(developerRole);
        Role adminRole = new SimpleRole("admin");
        identityManager.add(adminRole);
        Group picketBoxGroup = new SimpleGroup("The PicketBox Group");
        identityManager.add(picketBoxGroup);
        identityManager.grantRole(adminUser, developerRole);
        identityManager.grantRole(adminUser, adminRole);
        identityManager.addToGroup(adminUser, picketBoxGroup);
    }

    private void createPicketBoxManager() throws AuthenticationException {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.identityManager().jpaStore();
        PicketBoxConfiguration configuration = builder.build();
        this.picketBoxManager = new DefaultPicketBoxManager(configuration);
        picketBoxManager.start();
    }
}
