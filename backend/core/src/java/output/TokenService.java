package output;

import model.User;

public interface TokenService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
}
