package boot.spring.config;

// Import necessary annotations and classes for WebSocket configuration

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket configuration handler.
 * This class is marked with @Configuration to indicate that it contains bean definitions
 * for application context configuration. Specifically, it configures WebSocket support
 * for the application.
 */
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter's role
     * <p>
     * This bean will automatically register any WebSocket endpoints declared with the @ServerEndpoint annotation.
     * The ServerEndpointExporter is a necessary bean to configure if you're using server-endpoint based WebSocket configuration.
     *
     * @return ServerEndpointExporter - The bean that enables automatic registration of WebSocket endpoints.
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
