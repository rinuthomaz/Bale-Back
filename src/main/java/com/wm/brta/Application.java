package com.wm.brta;

import javax.servlet.Filter;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import com.wm.brta.security.AuthenticationFilter;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
    
	private @Autowired
	AutowireCapableBeanFactory beanFactory;

	
	public Application(){
		super();
		setRegisterErrorPageFilter(false);
	}
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    protected SpringApplicationBuilder configure(

            SpringApplicationBuilder application) {

        return application.sources(Application.class);
} 
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter myFilter = new AuthenticationFilter();
        beanFactory.autowireBean(myFilter);
        registration.setFilter(myFilter);
        registration.addUrlPatterns("/app/service/*");
        return registration;
    }
    
}