package usecase;

import exception.ResourceNotFoundException;
import input.ActivateUserInput;
import model.User;
import output.UserRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class ActivateUserScheduled implements ActivateUserInput {
    private final UserRepository userRepository;
    private final Clock clock;

    public ActivateUserScheduled(UserRepository userRepository, Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    @Override
    public void activateUser() {

        List<User> users= userRepository.findUsersByStatusPending();
        if(users.isEmpty()){
           return;
        }
        LocalDateTime now = LocalDateTime.now(clock);
        //realiza la operacion por cada elemento
        users.forEach(user -> {user.evaluateExpired(now);});
        //guarda los usuarios evaluados por el metodo

        userRepository.saveUserEvaluate(users);

    }
}
