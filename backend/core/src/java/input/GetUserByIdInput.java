package input;

import model.User;

public interface GetUserByIdInput {
    public User getUserById(Long id);
}
