package alysondev.lanchonete;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requer banco de dados real configurado - coberto por outros testes com Mockito/Testcontainers")
class LanchoneteApplicationTests {

	@Test
	void contextLoads() {
	}

}
