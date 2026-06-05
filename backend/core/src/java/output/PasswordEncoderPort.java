package output;

public interface PasswordEncoderPort {
    String encode(String password);
    boolean matches(String passwordIngressed,String passwordHashUser);
}
