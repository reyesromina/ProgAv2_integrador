package input;

import input.dto.TokenResponse;
import model.User;

public interface RegisterUserInput {

    TokenResponse registerUser(String email, String password);
}
