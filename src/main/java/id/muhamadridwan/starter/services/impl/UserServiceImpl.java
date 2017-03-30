/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.services.impl;

import id.muhamadridwan.starter.services.UserService;
import id.muhamadridwan.starter.models.Role;
import id.muhamadridwan.starter.models.User;
import id.muhamadridwan.starter.repositories.RoleRepository;
import id.muhamadridwan.starter.repositories.UserRepository;
import java.util.Calendar;
import java.util.List;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author mridwan
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUser(Long userId) {
        LOG.info("Get user by ID : " + userId);
        return userRepository.findOne(userId);
    }

    @Override
    public User getUser(String username) {
        LOG.info("Get user by Username : " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void addUser(User user) {
        LOG.info("Adding user : " + user.getUsername());
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        LOG.info("Update user : " + user.getUsername());
        User updateUser = userRepository.findOne(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setFirstname(user.getFirstname());
        updateUser.setLastname(user.getLastname());
        updateUser.setPassword(user.getPassword());
        updateUser.setRoles(user.getRoles());
        updateUser.setLastAccessedDate(user.getLastAccessedDate());
        updateUser.setEmail(user.getEmail());
        userRepository.save(updateUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User deleteUser = getUser(userId);
        LOG.info("Deleting user : " + deleteUser.getUsername());
        userRepository.delete(deleteUser);
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void assignRoleToUser(Role role, User user) {
        LOG.info("Assign role = { " + role.getName() + " } to user -> " + user.getUsername());

        Role assignRole = roleRepository.findByName(role.getName());
        if (assignRole == null) {
            throw new NoResultException("Role does not exist");

        }

        User assignUser = userRepository.findByUsername(user.getUsername());
        if (assignUser == null) {
            throw new NoResultException("User does not exist");
        }

        assignUser.getRoles().add(assignRole);
        userRepository.save(assignUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Load user by username : " + username);

        User account = null;
        try {
            account = userRepository.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (account == null) {
            LOG.warn("NO SUCH USER!!");
            throw new UsernameNotFoundException("No such user" + username);
        } else if (account.getRoles().isEmpty()) {
            LOG.warn("User has no authorities");
            throw new UsernameNotFoundException("User" + username+" has no authorities");
        }
        
        account.setLastAccessedDate(Calendar.getInstance().getTime());
        
        try {
            userRepository.save(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return account;
    }

}
