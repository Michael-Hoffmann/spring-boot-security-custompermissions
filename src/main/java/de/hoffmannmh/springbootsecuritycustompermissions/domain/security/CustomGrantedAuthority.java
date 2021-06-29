package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Builder
@Data
public class CustomGrantedAuthority implements GrantedAuthority {

    private CustomRole role;
    private Collection<CountryCode> countryCodes = Collections.EMPTY_LIST;

    @Override
    public String getAuthority() {
        return role.name();
    }

    public boolean hasPermissionFor(CustomRole role, CountryCode countryCode) {
        return this.role.equals(role) && countryCodes.contains(countryCode);
    }

}
