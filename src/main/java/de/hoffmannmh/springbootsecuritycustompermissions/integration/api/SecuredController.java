package de.hoffmannmh.springbootsecuritycustompermissions.integration.api;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/{countryCode}")
public class SecuredController {

    @GetMapping
    @PreAuthorize("hasPermission(#countryCode, 'MY_ROLE_1')")
    public String byCountry(@PathVariable CountryCode countryCode, Authentication authentication) {
        return String.format("You're successfully authorized for country %s",countryCode);
    }

}
