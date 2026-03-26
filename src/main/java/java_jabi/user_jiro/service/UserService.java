package java_jabi.user_jiro.service;

import java_jabi.user_jiro.exception.UserException;
import java_jabi.user_jiro.model.Role;
import java_jabi.user_jiro.model.User;
import java_jabi.user_jiro.model.UserData;
import java_jabi.user_jiro.model.UserInfo;
import java_jabi.user_jiro.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder crypto;
    private final TaskExternalService tasks;

    private static String pattern() {

        final String ONE_DIGIT = "(?=.*[0-9])";
        final String LOWER_CASE = "(?=.*[a-z])";
        final String UPPER_CASE = "(?=.*[A-Z])";
        final String SPECIAL_CHAR = "(?=.*[_@#$%^&+=])";
        final String NO_SPACE = "(?=\\S+$)";
        final String MIN_MAX_CHAR = ".{8,16}";
        return ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserInfo addUser(UserData userData) {
        User user = User.builder().login(userData.login()).password(userData.password()).role(userData.role()).build();
        validateUserData(user);
        user.setPassword(crypto.encode(user.getPassword()));
        return users.insert(user);
    }

    @Transactional(readOnly = true)
    public UserInfo getById(Long id) {
        return users.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (!tasks.checkExistTasksByUser(id)) {
            users.delete(id);
        } else {
            throw new UserException("У пользователя есть задачи в работе.");
        }
    }

    @Transactional(readOnly = true)
    public UserInfo getUser(Long id) {
        return users.getUser(id);
    }

    @Transactional(readOnly = true)
    public Boolean checkUser(Long id) {
        UserInfo user = users.getById(id);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional(readOnly = true)
    public Boolean checkHistUser(Long id) {
        UserInfo user = users.getUser(id);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    private void validateUserData(User user) {
        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Не указан логин");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new UserException("Не указан пароль");
        }
        if (!user.getPassword().matches(pattern())) {
            throw new UserException("Пароль не соотвествует требованиям: от 8 до 16 символов, не содержит пробелы, может включать цифры, буквы и часть спец символов");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserInfo setRole(Long id, Role role) {
        return users.setRole(id, role);
    }

    @Transactional(readOnly = true)
    public String getRole(Long id) {
        return users.getUser(id).getRole().toString();
    }
}
