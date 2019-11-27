package dev.firtch.myloginapp.dao;

import dev.firtch.myloginapp.model.Profile;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ProfileDao {

    Profile findById(Long id) throws DataAccessException;
    Profile findByEmail(String email) throws DataAccessException;
    List<Profile> findAll();

    boolean save(Profile profile);
    boolean update(Profile profile);
    boolean delete(Profile profile);

}
