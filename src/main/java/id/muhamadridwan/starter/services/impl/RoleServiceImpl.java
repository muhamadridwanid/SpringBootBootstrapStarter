/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.services.impl;

import id.muhamadridwan.starter.services.RoleService;
import id.muhamadridwan.starter.models.Permission;
import id.muhamadridwan.starter.models.Role;
import id.muhamadridwan.starter.repositories.PermissionRepository;
import id.muhamadridwan.starter.repositories.RoleRepository;
import java.util.List;
import javax.persistence.NoResultException;
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
public class RoleServiceImpl implements RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    private PermissionRepository permissionRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void addRole(Role role) {
        LOG.info("Add role : " + role.getName());
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Role role) {
        LOG.info("Update role : " + role.getName());
        Role updateRole = roleRepository.findOne(role.getId());
        updateRole.setName(role.getName());
        roleRepository.save(updateRole);
    }

    @Override
    public void deleteRole(Long id) {
        Role deleteRole = getRole(id);
        LOG.info("Delete role : " + deleteRole.getName());
        roleRepository.delete(deleteRole);
    }

    @Override
    public Role getRole(Long id) {
        LOG.info("Get role by id : " + id);
        return roleRepository.findOne(id);
    }

    @Override
    public Role getRole(String roleName) {
        LOG.info("Get role by name : " + roleName);
        return roleRepository.findByName(roleName);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void assignPermissionToRole(Permission permission, Role role) {
        LOG.info("Assign permission = { " + permission.getName() + " } to role -> " + role.getName());

        Permission assignPermission = permissionRepository.findByName(permission.getName());
        if (assignPermission == null) {
            throw new NoResultException("Permission does not exist");
        }

        Role assignRole = roleRepository.findByName(role.getName());
        if (assignRole == null) {
            throw new NoResultException("Role does not exist");
        }

        assignRole.getPermission().add(assignPermission);
        roleRepository.save(assignRole);
    }

}
