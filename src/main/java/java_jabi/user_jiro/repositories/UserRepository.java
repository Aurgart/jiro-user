package java_jabi.user_jiro.repositories;

import java_jabi.user_jiro.model.User;
import java_jabi.user_jiro.model.UserInfo;
import java_jabi.user_jiro.repositories.Mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {
    private static final String INSERT = """
            INSERT INTO jiro_user.user(login, password)
            VALUES (:login, :password)
            RETURNING *;
            """;
    private static final String DELETE = """
            UPDATE jiro_user.user
            SET is_deleted = TRUE
            WHERE id = :id;
            """;
    private static final String GET_BY_ID = """
            SELECT *
            FROM jiro_user.user
            WHERE id = :id
            AND is_deleted = false
            """;
    private static final String GET_USER = """
            SELECT *
            FROM jiro_user.user
            WHERE id = :id
            """;

    private final UserMapper userMapp;
    private final NamedParameterJdbcTemplate jbcTemplate;

    public UserInfo insert(User user) {
        return jbcTemplate.queryForObject(INSERT, userParamForSql(user), userMapp);
    }

    public void delete(Long id) {
        jbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    public UserInfo getById(Long id) {
        return jbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapp);
    }

    public UserInfo getUser(Long id) {
        return jbcTemplate.queryForObject(GET_USER, new MapSqlParameterSource("id", id), userMapp);
    }

    public MapSqlParameterSource userParamForSql(User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("login", user.getLogin());
        params.addValue("password", user.getPassword());
        params.addValue("is_deleted", user.getIsDeleted());

        return params;
    }
}
