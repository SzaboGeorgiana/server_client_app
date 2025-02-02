package com.example.restservices1;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class RestServices1Application {
//
//	public static void main(String[] args) {
//		SpringApplication.run(RestServices1Application.class, args);
//	}
//
//}
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class RestServicesFestivalApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(RestServicesFestivalApplication.class, args);
//    }
//
//}
//packge start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import festival.persistence.repository.*;
import com.example.restservices1.ctrl.RestController1;
import org.springframework.context.annotation.Primary;

//
//@ComponentScan(basePackages = "you-package-name*")
//@EnableJpaRepositories(basePackages = "repository-package")
//@EntityScan(basePackages = "entities-package")

@ComponentScan({"com.example.restservices1.ctrl","festival.persistence.repository"})
@SpringBootApplication
public class RestServices1Application {
	public static void main(String[] args) {

		SpringApplication.run(RestServices1Application.class, args);
	}

	@Bean(name="props")
	@Primary
	public Properties getBdProperties(){
		Properties props = new Properties();
		try {
			System.out.println("Searching bd.config in directory "+((new File(".")).getAbsolutePath()));
			props.load(new FileReader("bd.config"));
		} catch (IOException e) {
			System.err.println("Configuration file bd.cong not found" + e);

		}
		return props;
	}
}
