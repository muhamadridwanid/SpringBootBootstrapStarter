/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.services;

import id.muhamadridwan.starter.models.Permission;
import id.muhamadridwan.starter.models.Role;
import java.util.List;

/**
 *
 * @author mridwan
 */
public interface RoleService {

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);

    Role getRole(Long id);

    Role getRole(String roleName);

    List<Role> getRoles();

    void assignPermissionToRole(Permission permission, Role role);

}
