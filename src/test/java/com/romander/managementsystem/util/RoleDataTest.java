package com.romander.managementsystem.util;

import com.romander.managementsystem.model.Role;

public class RoleDataTest {
    public static Role getRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName(Role.RoleName.USER);
        return role;
    }
}