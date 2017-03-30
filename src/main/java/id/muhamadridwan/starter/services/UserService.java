/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.services;

import id.muhamadridwan.starter.models.Role;
import id.muhamadridwan.starter.models.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author mridwan
 */
public interface UserService extends UserDetailsService {

    User getUser(Long userId);

    User getUser(String username);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId);

    List<User> getUser();

    void assignRoleToUser(Role role, User user);

}
