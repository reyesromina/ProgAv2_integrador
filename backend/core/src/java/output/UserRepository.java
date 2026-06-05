package output;

import model.User;

import java.util.List;

public interface UserRepository {
    boolean existByEmail(String email);
    User findByEmail(String email);
    User saveUser(User user);
    User findUserById(Long id);
    List<User> findUsersByStatusPending();
    void saveUserEvaluate(List<User> users);
}
