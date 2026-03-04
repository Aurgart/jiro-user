package java_jabi.user_jiro.service;

import java_jabi.user_jiro.exception.UserException;
import java_jabi.user_jiro.model.User;
import java_jabi.user_jiro.model.UserInfo;
import java_jabi.user_jiro.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder crypto;

    public UserInfo addUser(User user){
        validateUserData(user);
        user.setPassword(crypto.encode(user.getPassword()));
        user = users.insert(user);
        return userToUserInfo(user);
    }

    public UserInfo getById(Long id){
        User user = users.getById(id);
        return userToUserInfo(user);
    }

    public UserInfo getUser(Long id){
        User user = users.getUser(id);
        return userToUserInfo(user);
    }
    public Boolean checkUser(Long id){
        User user = users.getById(id);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }
    public Boolean checkHistUser(Long id){
        User user = users.getUser(id);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }


    private UserInfo userToUserInfo(User user){
        UserInfo tmp = new UserInfo();
        tmp.setId(user.getId());
        tmp.setLogin(user.getLogin());
        tmp.setIs_deleted(user.getIs_deleted());
        return tmp;
    }

    private void validateUserData(User user) {
        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Не указан логин");
        }
        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Не указан пароль");
        }
        if(!user.getPassword().matches(pattern())){
            throw new UserException("Пароль не соотвествует требованиям: от 8 до 16 символов, не содержит пробелы, может включать цифры, буквы и часть спец символов");
        }
    }

    private static String pattern() {
        final boolean SPECIAL_CHAR_NEEDED = true;

        final String ONE_DIGIT = "(?=.*[0-9])";
        final String LOWER_CASE = "(?=.*[a-z])";
        final String UPPER_CASE = "(?=.*[A-Z])";
        final String SPECIAL_CHAR = "(?=.*[_@#$%^&+=])";
        final String NO_SPACE = "(?=\\S+$)";
        final String MIN_MAX_CHAR = ".{8, 16}";
        return ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;
    }
}
