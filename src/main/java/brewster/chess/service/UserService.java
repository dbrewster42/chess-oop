package brewster.chess.service;

import brewster.chess.exception.UserNotFound;
import brewster.chess.model.User;
import brewster.chess.model.request.UserRequest;
import brewster.chess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email) {
        return userRepository.save(new User(name, email));
    }
    public User createUser(UserRequest request) {
        return userRepository.save(new User(request.getName(), request.getEmail()));
    }

    public User getUser(String name) {
       return userRepository.findById(name).orElseThrow(UserNotFound::new);
    }
}
