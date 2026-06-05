package com.undec.persistence.crud;

import com.undec.persistence.entity.UserData;
import model.User;
import model.UserStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCrud extends CrudRepository<UserData, Long> {

    List<UserData> findByStatus(UserStatus status);
    boolean existsByEmail(String email);
    Optional<UserData> findByEmail(String email);

}