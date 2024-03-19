package boot.spring.config;

// Import the necessary SpringDoc and Spring Framework classes
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for SpringDoc.
 * This class is used to configure the OpenAPI documentation for the Spring Boot application.
 */
@Configuration
public class SpringDocConfig {

    /**
     * Creates a GroupedOpenApi bean that defines the configuration for the API documentation.
     * This bean specifies how the API documentation should be grouped and which paths should be included.
     *
     * @return GroupedOpenApi - The configured API group that will be used by SpringDoc to generate the OpenAPI documentation.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        // Builder pattern is used to create and configure the GroupedOpenApi instance
        return GroupedOpenApi.builder()
                .group("socket-chat") // Sets the name of the API group. This can be used to group multiple APIs under a single name in the documentation.
                .pathsToMatch("/**") // Specifies the paths to include in this API group. The pattern "/**" includes all paths in the application.
                .build(); // Builds and returns the configured GroupedOpenApi instance.
    }
}
