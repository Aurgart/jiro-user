package java_jabi.user_jiro.repositories.Mapper;

import java_jabi.user_jiro.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int rownum) throws SQLException{
        return User.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .is_deleted(rs.getBoolean("is_deleted"))
                .build();
    }
}
