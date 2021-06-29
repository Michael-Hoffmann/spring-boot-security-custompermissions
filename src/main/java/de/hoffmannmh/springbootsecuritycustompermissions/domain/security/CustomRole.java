package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

public enum CustomRole {
    UNKNOWN,
    MY_ROLE_1,
    MY_ROLE_2,
    MY_ROLE_3;

    public static CustomRole byName(String name) {
        try {
            return CustomRole.valueOf(name);
        } catch (IllegalArgumentException e) {
            return CustomRole.UNKNOWN;
        }
    }
}
