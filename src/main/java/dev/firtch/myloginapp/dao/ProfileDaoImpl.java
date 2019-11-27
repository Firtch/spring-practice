package dev.firtch.myloginapp.dao;

import dev.firtch.myloginapp.model.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfileDaoImpl implements ProfileDao {

    private final JdbcOperations jdbcOperations;

    private final String SELECT_BY_ID_QUERY = "SELECT * FROM profile WHERE id=?";
    private final String SELECT_BY_EMAIL_QUERY = "SELECT * FROM profile WHERE email=?";
    private final String SELECT_ALL = "SELECT * FROM profile";
    private final String SAVE_PROFILE_QUERY = "INSERT INTO profile(email, password, balance, created, enabled) values (?, ?, ?, ?, ?)";
    private final String UPDATE_PROFILE_QUERY = "UPDATE profile SET email=?, password=?, balance=?, created=?, enabled=? WHERE id=?";
    private final String DELETE_PROFILE_QUERY = "DELETE FROM profile WHERE id=?";

    public ProfileDaoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Profile findById(Long id) throws DataAccessException {
        return jdbcOperations.queryForObject(SELECT_BY_ID_QUERY,new Object[] {id}, new ProfileMapper());
    }

    @Override
    public Profile findByEmail(String email) throws DataAccessException {
        return jdbcOperations.queryForObject(SELECT_BY_EMAIL_QUERY, new Object[] {email}, new ProfileMapper());
    }

    @Override
    public List<Profile> findAll() {
        return jdbcOperations.query(SELECT_ALL, new ProfileMapper());
    }

    @Override
    public boolean save(Profile profile) {
        int status = jdbcOperations.update(SAVE_PROFILE_QUERY, profile.getEmail(), profile.getPassword(), profile.getBalance(), profile.getCreated(), profile.isEnabled());
        return status != 0;
    }

    @Override
    public boolean update(Profile profile) {
        int status = jdbcOperations.update(UPDATE_PROFILE_QUERY, profile.getEmail(), profile.getPassword(), profile.getBalance(), profile.getCreated(), profile.isEnabled(), profile.getId());
        return status != 0;
    }

    @Override
    public boolean delete(Profile profile) {
        int status = jdbcOperations.update(DELETE_PROFILE_QUERY, profile.getId());
        return status != 0;
    }

    private static final class ProfileMapper implements RowMapper<Profile> {

        public Profile mapRow(ResultSet resultSet, int i) throws SQLException {
            Profile profile = new Profile();
            profile.setId(resultSet.getLong("id"));
            profile.setEmail(resultSet.getString("email"));
            profile.setBalance(resultSet.getBigDecimal("balance"));
            profile.setPassword(resultSet.getString("password"));
            profile.setCreated(resultSet.getDate("created"));
            profile.setEnabled(resultSet.getBoolean("enabled"));
            return profile;
        }
    }
}
