package br.com.savia.validasenha;

import br.com.savia.validasenha.service.CheckPasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CheckPasswordApplicationTests {

	private CheckPasswordService checkPasswordService;

	@BeforeEach
	void setUp() {
		checkPasswordService = new CheckPasswordService();
	}

	@Test
	void validPasswordShouldReturnTrue() throws ExecutionException, InterruptedException {
		String password = "Daniel@260479"; // valid
		boolean result = checkPasswordService.checkPwd(password).get();
		assertTrue(result);
	}

	@Test
	void shortPasswordShouldReturnFalse() throws ExecutionException, InterruptedException {
		String password = "Dan1!-"; // too short
		boolean result = checkPasswordService.checkPwd(password).get();
		assertFalse(result);
	}

	@Test
	void passwordWithoutDigitShouldReturnFalse() throws ExecutionException, InterruptedException {
		String password = "Daniel@ABC";
		boolean result = checkPasswordService.checkPwd(password).get();
		assertFalse(result);
	}

	@Test
	void passwordWithoutLowercaseShouldReturnFalse() throws ExecutionException, InterruptedException {
		String password = "DANIEL@260479";
		boolean result = checkPasswordService.checkPwd(password).get();
		assertFalse(result);
	}

	@Test
	void passwordWithoutUppercaseShouldReturnFalse() throws ExecutionException, InterruptedException {
		String password = "daniel@260479";
		boolean result = checkPasswordService.checkPwd(password).get();
		assertFalse(result);
	}

	@Test
	void passwordWithoutSpecialCharShouldReturnFalse() throws ExecutionException, InterruptedException {
		String password = "Daniel260479";
		boolean result = checkPasswordService.checkPwd(password).get();
		assertFalse(result);
	}

	@Test
	void passwordWithRepeatedCharsShouldReturnFalse() throws ExecutionException, InterruptedException {
		String password = "Daaniel@260479"; // 'A' repeated
		boolean result = checkPasswordService.checkPwd(password).get();
		assertFalse(result);
	}
}
