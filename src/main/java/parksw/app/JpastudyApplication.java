package parksw.app;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpastudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpastudyApplication.class, args);
	}

	// Entity에서 지연로딩된 객체를 맵핑할 때 무한루프에 빠지지 않도록 도와주는 모듈
	// 그러나 가급적이 모듈을 사용하기 보다 DTO로 변환하는 것이 좋다.
	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}
}