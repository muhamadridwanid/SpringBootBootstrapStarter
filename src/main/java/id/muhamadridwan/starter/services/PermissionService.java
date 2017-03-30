/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.services;

import id.muhamadridwan.starter.models.Permission;
import java.util.List;

/**
 *
 * @author mridwan
 */
public interface PermissionService {

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    void deletePermission(Long id);

    Permission getPermission(Long id);

    Permission getPermission(String permissionName);

    List<Permission> getPermissions();

}
