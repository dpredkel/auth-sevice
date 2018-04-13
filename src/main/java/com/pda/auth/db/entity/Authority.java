package com.pda.auth.db.entity;

import java.util.HashMap;
import java.util.Map;

public enum Authority {
    USER, ADMIN;

    private static final Map<String, Authority> map = new HashMap<>(Authority.values().length);

    static {
        for (Authority type : Authority.values()) {
            map.put(type.name(), type);
        }
    }

    public static Authority getByName(String authority) {
        return map.get(authority);
    }
}