package run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import run.controller.*;

@SpringBootApplication
@ComponentScan({"de.htw-berlin.ai.run.controller", "de.htw-berlin.ai.run.service", "de.htw-berlin.ai.run.repository", "de.htw-berlin.ai.run.client","de.htw-berlin.ai.run.user"})
@EntityScan(basePackages = "de.htw-berlin.ai.bean")
@ComponentScan(basePackageClasses=UserController.class)
public class Application {
	
	
    public static void main(String[] args) {
    	System.out.println("läuft");
        SpringApplication.run(Application.class, args);
    }
}

/*
@ComponentScan({"de.htw-berlin.ai.run"})
@EnableJpaRepositories(basePackages = "de.htw-berlin.ai.repository")
@EntityScan(basePackages = "de.htw-berlin.ai.bean")
*/