package ${project.basepackage};

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.fiberhome.smartms.boot.SmartMsApp;

@EnableDiscoveryClient
@SpringBootApplication
public class TestApplication {
  public static void main(String[] args) {
    SmartMsApp.run(TestApplication.class, args);
  }
}
