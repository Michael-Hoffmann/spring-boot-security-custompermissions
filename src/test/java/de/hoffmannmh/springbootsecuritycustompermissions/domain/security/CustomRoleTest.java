package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomRoleTest {

    @Test
    public void testFallbackForUnknown() {
        CustomRole customRole = CustomRole.byName("DUMMY");
        Assertions.assertThat(customRole).isEqualTo(CustomRole.UNKNOWN);
    }

}
