package mx.sadead.spring.joinfaces.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class WebServletInitializer extends SpringBootServletInitializer {

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

}
