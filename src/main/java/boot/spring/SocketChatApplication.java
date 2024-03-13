package boot.spring;

import boot.spring.config.ShiroConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(ShiroConfig.class)
public class SocketChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketChatApplication.class, args);
	}

}
