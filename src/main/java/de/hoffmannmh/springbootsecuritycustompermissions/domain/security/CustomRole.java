package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

public enum CustomRole {
    UNKNOWN,
    MY_ROLE;

    public static CustomRole byName(String name) {
        try {
            return CustomRole.valueOf(name);
        } catch (IllegalArgumentException e) {
            return CustomRole.UNKNOWN;
        }
    }
}
