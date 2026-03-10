package java_jabi.user_jiro.repositories.Mapper;

import java_jabi.user_jiro.model.User;
import java_jabi.user_jiro.model.UserInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserInfo>{

    @Override
    public UserInfo mapRow(ResultSet rs, int rownum) throws SQLException{
        return UserInfo.builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .isDeleted(rs.getBoolean("is_deleted"))
                .build();
    }
}
