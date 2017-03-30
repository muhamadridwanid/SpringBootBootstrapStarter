/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.bootload;

import id.muhamadridwan.starter.models.Permission;
import id.muhamadridwan.starter.models.Role;
import id.muhamadridwan.starter.models.User;
import id.muhamadridwan.starter.services.PermissionService;
import id.muhamadridwan.starter.services.RoleService;
import id.muhamadridwan.starter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author mridwan
 */
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        System.out.println("Starting data loading");
        User adminUser = new User("admin", passwordEncoder.encode("admin"), "admin", "adminLastname", "admin@gmail.com");
        User testUser = new User("test", passwordEncoder.encode("test"), "test", "testLastname", "test@gmail.com");

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        Permission perm1 = new Permission("PERM_ADMIN_1");
        Permission perm2 = new Permission("PERM_ADMIN_2");
        Permission perm3 = new Permission("PERM_USER_1");
        Permission perm4 = new Permission("PERM_USER_2");

        permissionService.addPermission(perm1);
        permissionService.addPermission(perm2);
        permissionService.addPermission(perm3);
        permissionService.addPermission(perm4);

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

        adminUser.setEnabled(true);
        testUser.setEnabled(true);

        userService.addUser(adminUser);
        userService.addUser(testUser);

        roleService.assignPermissionToRole(perm1, roleAdmin);
        roleService.assignPermissionToRole(perm2, roleAdmin);
        roleService.assignPermissionToRole(perm3, roleUser);
        roleService.assignPermissionToRole(perm4, roleUser);

        userService.assignRoleToUser(roleAdmin, adminUser);
        userService.assignRoleToUser(roleUser, adminUser);
        userService.assignRoleToUser(roleUser, testUser);

    }

}
