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

package mx.sadead.spring.joinfaces;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * JoinFaces Example Configuration class.
 * @author Marcelo Fernandes
 */
@SpringBootApplication
public class JoinFacesExampleApplication extends SpringBootServletInitializer {

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("jsf.primefaces.THEME", "#{app.theme}");
                servletContext.setInitParameter("javax.faces.CONFIG_FILES", "/WEB-INF/config/faces/faces-config.xml");
                servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
            }
        };
    }

	/**
	* Main method.
	*/
	public static void main(String[] args) {
		SpringApplication.run(JoinFacesExampleApplication.class, args);
	}
}
