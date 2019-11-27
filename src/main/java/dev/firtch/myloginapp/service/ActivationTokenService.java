package dev.firtch.myloginapp.service;

import dev.firtch.myloginapp.model.ActivationToken;

import java.util.Optional;

public interface ActivationTokenService {

    Optional<ActivationToken> findById(Long id);
    Optional<ActivationToken> findByToken(String token);

    boolean save(ActivationToken activationToken);
    boolean update(ActivationToken activationToken);
    boolean delete(ActivationToken activationToken);
}
