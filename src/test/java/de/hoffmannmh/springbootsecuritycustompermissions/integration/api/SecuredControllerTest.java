package de.hoffmannmh.springbootsecuritycustompermissions.integration.api;

import de.hoffmannmh.springbootsecuritycustompermissions.security.WithMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuredControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void byCountryForbiddenNoUser() throws Exception {
        mockMvc
                .perform(get("/api/v1/DE"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(role = "DUMMY", countries = {"DE", "BE"})
    public void byCountryForbiddenMissingRole() throws Exception {
        mockMvc
                .perform(get("/api/v1/DE"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(role = "MY_ROLE", countries = {"PL", "BE"})
    public void byCountryForbiddenMissingCountry() throws Exception {
        mockMvc
                .perform(get("/api/v1/DE"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(role = "MY_ROLE", countries = {"PL", "BE"})
    public void byCountryBadRequestUnknownSourceCountry() throws Exception {
        mockMvc
                .perform(get("/api/v1/xx"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(role = "MY_ROLE", countries = {"DE", "BE"})
    public void byCountryAuthorized() throws Exception {
        mockMvc
                .perform(get("/api/v1/DE"))
                .andExpect(status().isOk())
                .andExpect(content().string("You're successfully authorized for country DE"))
                .andReturn();

        mockMvc
                .perform(get("/api/v1/BE"))
                .andExpect(status().isOk())
                .andExpect(content().string("You're successfully authorized for country BE"))
                .andReturn();
    }
}

