package dev.firtch.myloginapp.dao;

import dev.firtch.myloginapp.model.ActivationToken;

public interface ActivationTokenDao {

    ActivationToken findById(Long id);
    ActivationToken findByToken(String token);

    boolean save(ActivationToken activationToken);
    boolean update(ActivationToken activationToken);
    boolean delete(ActivationToken activationToken);

}
