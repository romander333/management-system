package com.romander.managementsystem.util;

import com.romander.managementsystem.model.Role;
import com.romander.managementsystem.model.User;

import java.util.Set;

import static com.romander.managementsystem.util.RoleDataTest.getRole;

public class UserDataTest {

    public static User getSampleUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("romander@gmail.com");
        user.setFirstName("Roman");
        user.setLastName("Luch");
        user.setPassword("$2a$12$k5M6AyJ4itLQTb6KgNZsCeTROTmcRGE0AUr9Z0Kk/Mr9aDM8LPkYq");
        Role role = getRole();
        user.setRoles(Set.of(role));
        return user;
    }
}
