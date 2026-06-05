package com.undec.persistence.repository;

import com.undec.persistence.crud.UserRepositoryCrud;
import com.undec.persistence.entity.UserData;
import com.undec.persistence.until.UserMapper;
import model.User;
import model.UserStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import output.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public class UserRepositoryImpl implements UserRepository {

    private UserRepositoryCrud userRepositoryCrud;

    public UserRepositoryImpl(UserRepositoryCrud userRepositoryCrud) {
        this.userRepositoryCrud = userRepositoryCrud;
    }



    @Override
    public boolean existByEmail(String email) {

        return userRepositoryCrud.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email){
        return userRepositoryCrud.findByEmail(email)
                .map(UserMapper::mapToUserDomain)
                .orElse(null);
    }

    @Override
    public User saveUser(User user) {

        UserData userData =UserMapper.mapToUserData(user);
        userRepositoryCrud.save(userData);

        return UserMapper.mapToUserDomain(userData);
    }

    @Override
    public User findUserById(Long id) {

        return  userRepositoryCrud.findById(id)
        .map(UserMapper::mapToUserDomain)
                .orElse(null);
    }

    @Override
    public List<User> findUsersByStatusPending() {

       List<UserData> userDataList= userRepositoryCrud.findByStatus(UserStatus.PENDING);

        List<User> userList=userDataList.
                stream().
                map(UserMapper::mapToUserDomain).
                collect(Collectors.toList());

        return userList;
    }

    @Transactional
    @Override
    public void saveUserEvaluate(List<User> users) {
        List<UserData> userDataList = users.
                stream().
                map(UserMapper::mapToUserData).
                collect(Collectors.toList());

            userRepositoryCrud.saveAll(userDataList);
    }
}
