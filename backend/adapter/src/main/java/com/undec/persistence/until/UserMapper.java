package com.undec.persistence.until;

import com.undec.persistence.entity.UserData;
import model.User;

public class UserMapper {

    public static UserData mapToUserData(User user){

        if (user == null) return null;

        UserData data = new UserData(
               user.getId(),
                user.getEmailUser(),
                user.getPassword(),
                user.getStatus(),
                user.getActivationCode(),
                user.getCreatedAt(),
                user.getActivationExpiredAt()
        );
        if (user.getId() != null) {
            data.setId(user.getId());
        }

        return data;
    }
    public static User mapToUserDomain(UserData data) {
        if (data == null) return null;
        return User.constructor(
                data.getId(),
                data.getEmail(),
                data.getPassword(),
                data.getStatus(),
                data.getActivationCode(),
                data.getActivationExpiredAt(),
                data.getCreatedAt()


        );
    }

}
