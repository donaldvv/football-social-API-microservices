package com.don.discoveryserver;

// Make the Dashboard require the uname and password specified in .properties

// Make each microservice authenticate itself when registering themselves to the Discovery Server
//      To do this:
//          - make all the microservices contain the username & password in the: eureka.client.serviceUrl.defaultZone = http://localhost:8010/eureka,
//              meaning they should be like this: eureka.client.serviceUrl.defaultZone = http://test:test@localhost:8010/eureka

//  *** BEST PRACTICE:since it will be same for all ms, better to put it in Config Server. (I skipped it to save some effort)

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringAntMatchers("/eureka/**");
        super.configure(http);
/*                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();*/
    }
}
