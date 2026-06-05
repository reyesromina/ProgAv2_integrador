package input;

import input.dto.TokenResponse;


public interface LoginUserInput {
    TokenResponse login (String email, String password);
}
