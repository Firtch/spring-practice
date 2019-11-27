package dev.firtch.myloginapp.dao;

import dev.firtch.myloginapp.model.ActivationToken;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ActivationTokenDaoImpl implements ActivationTokenDao {

    private final String SELECT_BY_ID_QUERY = "SELECT * FROM activation_token WHERE id=?";
    private final String SELECT_BY_TOKEN_QUERY = "SELECT * FROM activation_token WHERE token=?";
    private final String SAVE_TOKEN_QUERY = "INSERT INTO activation_token(token, profile) values (?, ?)";
    private final String UPDATE_TOKEN_QUERY = "UPDATE activation_token SET token=?, profile=? WHERE id=?";
    private final String DELETE_TOKEN_QUERY = "DELETE FROM activation_token WHERE id=?";

    private final JdbcOperations jdbcOperations;

    public ActivationTokenDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public ActivationToken findById(Long id) {
        return jdbcOperations.queryForObject(SELECT_BY_ID_QUERY,new Object[] {id}, new ActivationTokenMapper());
    }

    @Override
    public ActivationToken findByToken(String token) {
        return jdbcOperations.queryForObject(SELECT_BY_TOKEN_QUERY,new Object[] {token}, new ActivationTokenMapper());
    }

    @Override
    public boolean save(ActivationToken activationToken) {
        int status = jdbcOperations.update(SAVE_TOKEN_QUERY, activationToken.getToken(), activationToken.getProfile());
        return status != 0;
    }

    @Override
    public boolean update(ActivationToken activationToken) {
        int status = jdbcOperations.update(UPDATE_TOKEN_QUERY, activationToken.getToken(), activationToken.getProfile(), activationToken.getId());
        return status != 0;
    }

    @Override
    public boolean delete(ActivationToken activationToken) {
        int status = jdbcOperations.update(DELETE_TOKEN_QUERY, activationToken.getId());
        return status != 0;
    }

    private static final class ActivationTokenMapper implements RowMapper<ActivationToken> {

        @Override
        public ActivationToken mapRow(ResultSet resultSet, int i) throws SQLException {
            ActivationToken activationToken = new ActivationToken();
            activationToken.setId(resultSet.getLong("id"));
            activationToken.setToken(resultSet.getString("token"));
            activationToken.setProfile(resultSet.getString("profile"));
            return activationToken;
        }
    }
}
