package ${project.basepackage};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ${project.appName}Application {
  public static void main(String[] args) {
    SpringApplication.run(${project.appName}Application.class,args);
  }
}
