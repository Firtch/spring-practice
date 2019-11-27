package dev.firtch.myloginapp.service;

import dev.firtch.myloginapp.dao.ProfileDao;
import dev.firtch.myloginapp.exception.UniqueEmailException;
import dev.firtch.myloginapp.model.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;

    public ProfileServiceImpl(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        try {
            return Optional.of(profileDao.findById(id));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        try {
            return Optional.of(profileDao.findByEmail(email));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Profile> findAll() {
        return profileDao.findAll();
    }

    @Override
    public boolean save(Profile profile) {
        try {
            return profileDao.save(profile);
        } catch (DuplicateKeyException ex) {
            throw new UniqueEmailException(String.format("Email %s, already registered", profile.getEmail()));
        }
    }

    @Override
    public boolean update(Profile profile) {
        return profileDao.update(profile);
    }

    @Override
    public boolean delete(Profile profile) {
        return profileDao.delete(profile);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Profile profile = profileDao.findByEmail(email);
            return new User(profile.getEmail(), profile.getPassword(), profile.isEnabled(), true, true, true, getAuthorities("USER"));
        } catch (EmptyResultDataAccessException ex) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }


}
