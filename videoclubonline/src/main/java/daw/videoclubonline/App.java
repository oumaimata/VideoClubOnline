package daw.videoclubonline;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class App extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SpringApplication.run(App.class, args);
	}

}
