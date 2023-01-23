package learn.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Health Check Controller.
 */
@RestController
public class HealthCheckController {


  @GetMapping("/health")
  public String healthCheck() {
    return "OK, It's working";
  }
}
