package java_jabi.user_jiro;

import java_jabi.user_jiro.model.User;
import java_jabi.user_jiro.model.UserData;
import java_jabi.user_jiro.repositories.UserRepository;
import java_jabi.user_jiro.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserJiroTests {

	@Mock
	private UserRepository userLogic;
	@Mock
	private PasswordEncoder crypto;

	@InjectMocks
	private UserService userService;

	@Test
	void createUserTest(){
		Assertions.assertDoesNotThrow(() -> {
			userService.addUser(new UserData("Testovic","1342jkLfs_1skl"));
		});
		RuntimeException excp = assertThrows(RuntimeException.class, () -> userService.addUser(
				new UserData("Testovic","1111")));
		Assertions.assertNotNull(excp.getMessage());
	}

	private User testUser(Long id, String login, Boolean isDeleted,String pass) {
		return User.builder()
				.id(id)
				.login(login)
				.isDeleted(isDeleted)
				.password(pass)
				.build();
	}
}
