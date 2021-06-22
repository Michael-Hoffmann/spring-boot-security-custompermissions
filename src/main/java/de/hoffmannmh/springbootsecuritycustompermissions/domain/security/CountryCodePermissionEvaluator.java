package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class CountryCodePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(
            Authentication auth, Object countryCode, Object permission) {
        if (auth == null || countryCode == null || !(countryCode instanceof CountryCode) || !(permission instanceof String)){
            return false;
        }

        CustomRole customRole = CustomRole.byName((String) permission);
        CountryCode countryCode1 = (CountryCode) countryCode;

        return auth.getAuthorities()
                .stream()
                .filter(authority -> authority instanceof CustomGrantedAuthority)
                .anyMatch(authority ->
                        ((CustomGrantedAuthority) authority).hasPermissionFor(customRole, countryCode1));

    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
