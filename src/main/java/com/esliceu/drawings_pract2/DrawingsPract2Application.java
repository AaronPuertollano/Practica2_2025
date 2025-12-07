package com.esliceu.drawings_pract2;

import com.esliceu.drawings_pract2.filter.SessionInterceptor;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@SpringBootApplication
@Configuration
public class DrawingsPract2Application implements WebMvcConfigurer {

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${spring.datasource.username}")
    String datasourceUser;

    @Value("${spring.datasource.password}")
    String datasourcePassword;

	public static void main(String[] args) {
		SpringApplication.run(DrawingsPract2Application.class, args);
	}

    @Bean
    public DataSource dataSource(){
        MysqlDataSource mds = new MysqlDataSource();
        mds.setURL(datasourceUrl);
        mds.setUser(datasourceUser);
        mds.setPassword(datasourcePassword);
        return mds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/paint")
                .addPathPatterns("/private")
                .addPathPatterns("/public")
                .addPathPatterns("/trash")
                .addPathPatterns("/shared")
                .addPathPatterns("/edit")
                .addPathPatterns("/versions");
    }


}
