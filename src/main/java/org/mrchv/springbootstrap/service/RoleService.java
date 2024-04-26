package org.mrchv.springbootstrap.service;

import org.mrchv.springbootstrap.model.Role;

import java.util.List;

public interface RoleService {
    Role findRoleByName(String name);
    List<Role> findAllRoles();
    void saveRole(Role role);
}
