package org.example.bank2.security;

public class Authorities {

    public static final String ADMIN = "ADMIN";

    public static final String USER = "USER";

    public static final String ADMIN_AUTHORITY = "hasAuthority('" + ADMIN + "')";

    public static final String HAS_ANY_AUTHORITY = "hasAuthority('" + ADMIN + "','" + USER + "')";
}
