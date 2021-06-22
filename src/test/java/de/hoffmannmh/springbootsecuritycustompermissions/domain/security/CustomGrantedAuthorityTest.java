package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class CustomGrantedAuthorityTest {

    @Test
    public void hasPermissionFor() {

        CustomGrantedAuthority customGrantedAuthority = CustomGrantedAuthority.builder().role(CustomRole.MY_ROLE).countryCodes(Collections.singletonList(CountryCode.DE)).build();
        Assertions.assertThat(customGrantedAuthority.hasPermissionFor(CustomRole.MY_ROLE,CountryCode.DE)).isTrue();
        Assertions.assertThat(customGrantedAuthority.hasPermissionFor(CustomRole.UNKNOWN,CountryCode.DE)).isFalse();
        Assertions.assertThat(customGrantedAuthority.hasPermissionFor(CustomRole.MY_ROLE,CountryCode.UNDEFINED)).isFalse();

    }

}
