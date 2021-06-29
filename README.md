# Example of using country specific permissions with Spring Boot

This repository contains a Spring Boot example of using country specific permissions.

The main magic happens in the [custom PermissionEvalusator](src/main/java/de/hoffmannmh/springbootsecuritycustompermissions/domain/security/CountryCodePermissionEvaluator.java).

Here we try to evaluate the input arguments given in the [secured controller](src/main/java/de/hoffmannmh/springbootsecuritycustompermissions/integration/api/SecuredController.java).
`@PreAuthorize("hasPermission(#countryCode, 'MY_ROLE')")`

With this [configuration](src/main/java/de/hoffmannmh/springbootsecuritycustompermissions/domain/security/MethodSecurityConfiguration.java) we configure spring to use our custom evaluator. 

Please also check the [related tests](src/test/java/de/hoffmannmh/springbootsecuritycustompermissions/integration/api/SecuredControllerTest.java) to find out how it works.

## OAUTH2
To enable OAUTH2 please see [SecurityConfiguration](src/main/java/de/hoffmannmh/springbootsecuritycustompermissions/integration/oauth/SecurityConfiguration.java)

The main magic happens in the [CustomAuthenticationTokenConverter](src/main/java/de/hoffmannmh/springbootsecuritycustompermissions/integration/oauth/CustomAuthenticationTokenConverter.java)

To see how it works, please look at the [related tests](src/test/java/de/hoffmannmh/springbootsecuritycustompermissions/integration/oauth/CustomAuthenticationTokenTest.java)

Due to time I'm actually not able to implement tests for the complete OAUTH2 flow, but main stuff should be available.

Have fun!
