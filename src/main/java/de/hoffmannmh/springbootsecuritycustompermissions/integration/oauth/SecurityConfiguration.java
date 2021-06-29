package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;


@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CustomAuthenticationTokenConverter tokenConverter;
    private OauthIssuerProperties oauthIssuerProperties;

    public SecurityConfiguration(CustomAuthenticationTokenConverter tokenConverter, OauthIssuerProperties oauthIssuerProperties) {
        this.tokenConverter = tokenConverter;
        this.oauthIssuerProperties = oauthIssuerProperties;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().authenticated()
/* Please uncomment following line to configure spring to use OAUTH2 */
//                .and()
//                .oauth2ResourceServer(oauth2 -> {
//                    oauth2.authenticationManagerResolver(jwtIssuerAuthenticationManagerResolver());
//                    oauth2.jwt().jwtAuthenticationConverter(tokenConverter);
//                })
    ;
        //@formatter:on
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/error/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Empty
    }

    public JwtIssuerAuthenticationManagerResolver jwtIssuerAuthenticationManagerResolver() {
        return new JwtIssuerAuthenticationManagerResolver(oauthIssuerProperties.getWhitelist());
    }

}
