package test.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DataBaseTestConfig{
	
	@Value("${datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${datasource.url}")
	private String url;
	
	@Value("${datasource.username}")
	private String user;
	
	@Value("${datasource.password}")
	private String password;
	
	@Value("${hibernate.dialect}")
	private String dialect;
	
	@Value("${hbm2dll_auto}")
	private String hbm2dll_auto;
	
	@Bean
	public DataSource dataSource() {
		System.out.println("cl=" + driverClassName);
		
    	BasicDataSource ds = new BasicDataSource();

	    ds.setDriverClassName(driverClassName);
	    ds.setUrl(url);
	    ds.setUsername(user);
	    ds.setPassword(password);

	    return ds;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    Properties properties = new Properties();
	    
	    properties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
	    properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2dll_auto);
	    factory.setDataSource(dataSource);
	    factory.setPackagesToScan("main.java.com.epsm.electricPowerSystemModel.model.dispatch");
	    factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	    factory.setJpaProperties(properties);

	    return factory;
	}
	
	@Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }
}