/******************* Generated by FEGO Transformer Logic *******************/
package org.ejb.sample;

import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import javax.servlet.Servlet;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.ejb.sample.servlet.UserServlet;
import org.ejb.sample.servlet.ShoppingServlet;

@Configuration()
public class ServletBeanConfiguration {

    @Bean()
    public ServletRegistrationBean userservlet(UserServlet userServlet) {
        return buildServletRegistration(1, userServlet, "/user");
    }

    @Bean()
    public ServletRegistrationBean shoppingservlet(ShoppingServlet shoppingServlet) {
        return buildServletRegistration(1, shoppingServlet, "/shopping");
    }

    private ServletRegistrationBean buildServletRegistration(int loadOnStartup, Servlet servlet, String... urlPatterns) {
        ServletRegistrationBean registration = new ServletRegistrationBean();
        registration.setServlet(servlet);
        registration.setUrlMappings(Arrays.asList(urlPatterns));
        registration.setLoadOnStartup(loadOnStartup);
        return registration;
    }
}
