package ${project.basepackage};

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class TestApplication {
  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class,args)
  }
}
