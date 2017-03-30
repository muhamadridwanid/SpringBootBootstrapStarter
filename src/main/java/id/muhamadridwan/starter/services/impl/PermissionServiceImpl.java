/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.services.impl;

import id.muhamadridwan.starter.services.PermissionService;
import id.muhamadridwan.starter.models.Permission;
import id.muhamadridwan.starter.repositories.PermissionRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mridwan
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private PermissionRepository permissionRepository;

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void addPermission(Permission permission) {
        LOG.info("Add permission : " + permission.getName());
        permissionRepository.save(permission);

    }

    @Override
    public void updatePermission(Permission permission) {
        LOG.info("Update permission : " + permission.getName());
        Permission updatePermission = permissionRepository.findOne(permission.getId());
        updatePermission.setName(permission.getName());
        permissionRepository.save(updatePermission);
    }

    @Override
    public void deletePermission(Long id) {
        Permission deletePermission = getPermission(id);
        LOG.info("Delete permission : " + deletePermission.getName());
        permissionRepository.delete(deletePermission);
    }

    @Override
    public Permission getPermission(Long id) {
        LOG.info("Get permission Id : " + id );
        return permissionRepository.findOne(id);
    }

    @Override
    public Permission getPermission(String permissionName) {
        LOG.info("Get permission Name : " + permissionName );
        return permissionRepository.findByName(permissionName);        
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionRepository.findAll();
    }

}
