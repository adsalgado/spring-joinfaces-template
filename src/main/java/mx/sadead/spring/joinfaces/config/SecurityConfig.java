/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mx.sadead.spring.joinfaces.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mx.sadead.spring.joinfaces.config.security.SSUserDetailsService;

/**
 * Spring Security Configuration.
 *
 * @author Marcelo Fernandes
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ApplicationUsers.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SSUserDetailsService ssUserDetailsService;

	@SuppressFBWarnings("SPRING_CSRF_PROTECTION_DISABLED")
	@Override
	protected void configure(HttpSecurity http) {
		try {

			http.csrf().disable();
			http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/**.jsf", "/**.xhtml").permitAll()
				.antMatchers("/javax.faces.resource/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login.xhtml").permitAll()
				.failureUrl("/login.xhtml?error=true")
				.defaultSuccessUrl("/aplicacion/operacion/index.xhtml")
				.and()
				.logout()
				.logoutSuccessUrl("/index.xhtml")
				.deleteCookies("JSESSIONID");

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(ssUserDetailsService).passwordEncoder(passwordEncoder());
	}
}
