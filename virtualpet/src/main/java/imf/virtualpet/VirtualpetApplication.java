package imf.virtualpet;

import imf.virtualpet.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class VirtualpetApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualpetApplication.class, args);
	}

}
