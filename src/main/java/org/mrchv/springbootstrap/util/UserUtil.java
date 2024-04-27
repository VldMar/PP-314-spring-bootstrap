package org.mrchv.springbootstrap.util;

import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
    public static boolean isUserAdmin(UserDetails user) {
        return user
                .getAuthorities()
                .stream()
                .filter(role -> role.getAuthority().equals("ROLE_ADMIN"))
                .count() > 0;
    }
}
