package com.acme.leads.shared.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public class SecurityUtils {

    public static String USER_ID_CLAIM = "userId";
    public static String ADMIN_ID_CLAIM = "adminId";
    public static String TEACHER_ID_CLAIM = "teacherId";
    public static String STUDENT_ID_CLAIM = "studentId";

    public static String BEARER_PREFIX = "Bearer ";

    public static String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : "";
    }

    public static Claims getClaims() {
        return (Claims) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public static Long getUserId() {
        return getClaims().get(USER_ID_CLAIM, Long.class);
    }

    public static Long getAdminId() {
        return getClaims().get(ADMIN_ID_CLAIM, Long.class);
    }

    public static Long getTeacherId() {
        return getClaims().get(TEACHER_ID_CLAIM, Long.class);
    }

    public static Long getStudentId() {
        return getClaims().get(STUDENT_ID_CLAIM, Long.class);
    }

    public static boolean hasAuthority(String authority) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(authority));
    }
}