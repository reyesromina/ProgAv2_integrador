package usecase;

import input.GetUserByIdInput;
import model.User;
import org.springframework.web.client.ResourceAccessException;
import output.UserRepository;

public class GetUserByIdUseCase implements GetUserByIdInput {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {

        User user = userRepository.findUserById(id);
        if(user==null){
           throw new ResourceAccessException("User not found");

        }

        return user;
    }
}
