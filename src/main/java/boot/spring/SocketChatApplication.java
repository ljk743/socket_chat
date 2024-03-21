package boot.spring; // Defines the package name as boot.spring.

import boot.spring.config.ShiroConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

// The @SpringBootApplication annotation is a convenience annotation that adds all the following:
// - @Configuration: Tags the class as a source of bean definitions for the application context.
// - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
// - @ComponentScan: Tells Spring to scan the current package and its sub-packages for Spring components, configurations, and services.
@SpringBootApplication
@Import(ShiroConfig.class)
// The @Import annotation is used here to explicitly import the ShiroConfig class into the Spring application context.
public class SocketChatApplication {

    public static void main(String[] args) {
        // The main method serves as the entry point for the application.
        // It delegates to Spring Boot's SpringApplication.run method to bootstrap the application.
        // SocketChatApplication.class is passed as an argument to specify the primary Spring component.
        // The args array is also passed through to expose any command-line arguments.
        SpringApplication.run(SocketChatApplication.class, args);
    }
}
