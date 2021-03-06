/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.issuetracker.dao.api;


import com.issuetracker.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mgottval
 */
@Local
public interface UserDao {
    
    User getUserById(Long id);
    
    User getUserByEmail(String email);
    
    User getUserByUsername(String username);
    
    User getUserByName(String username);
    
    void addUser(User user);
    
    void updateUser(User user);
    
    void removeUser(User user);
    
    boolean isUsernameInUse(String username);
    
    boolean isEmailInUse(String email);
    
    List<User> getUsers();
    
    User loadUserIfPasswordMatches(String name, String password);
}
