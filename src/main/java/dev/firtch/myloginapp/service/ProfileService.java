package dev.firtch.myloginapp.service;

import dev.firtch.myloginapp.model.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface ProfileService extends UserDetailsService {

    Optional<Profile> findById(Long id);
    Optional<Profile> findByEmail(String email);
    List<Profile> findAll();

    boolean save(Profile profile);
    boolean update(Profile profile);
    boolean delete(Profile profile);

}
