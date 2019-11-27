package dev.firtch.myloginapp.service;

import dev.firtch.myloginapp.dao.ActivationTokenDao;
import dev.firtch.myloginapp.model.ActivationToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivationTokenServiceImpl implements ActivationTokenService {

    private final ActivationTokenDao activationTokenDao;

    public ActivationTokenServiceImpl(ActivationTokenDao activationTokenDao) {
        this.activationTokenDao = activationTokenDao;

    }

    @Override
    public Optional<ActivationToken> findById(Long id) {
        try {
            return Optional.of(activationTokenDao.findById(id));
        }catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ActivationToken> findByToken(String token) {
        try {
            return Optional.of(activationTokenDao.findByToken(token));
        }catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean save(ActivationToken activationToken) {
        return activationTokenDao.save(activationToken);
    }

    @Override
    public boolean update(ActivationToken activationToken) {
        return activationTokenDao.update(activationToken);
    }

    @Override
    public boolean delete(ActivationToken activationToken) {
        return activationTokenDao.delete(activationToken);
    }
}
