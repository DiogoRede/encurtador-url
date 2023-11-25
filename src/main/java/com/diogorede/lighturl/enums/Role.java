package com.diogorede.lighturl.enums;

public enum Role {
    ADMIN("ADMIN_ROLE"),
    USUARIO("USUARIO_ROLE");

    private final String auth;

    Role(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }
}
