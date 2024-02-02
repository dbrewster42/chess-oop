package brewster.chess.service;

import brewster.chess.exception.UserNotFound;
import brewster.chess.model.ChessGame;
import brewster.chess.model.Player;
import brewster.chess.model.User;
import brewster.chess.model.request.UserRequest;
import brewster.chess.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    public User createUser(UserRequest request) {
        return userRepository.save(new User(request.getName(), request.getEmail()));
    }
    public User createQuickUser(String name) {
        return userRepository.save(new User(name, name + "@gmail.com"));
    }
    public User getUser(String name) {
       return userRepository.findById(name).orElseThrow(UserNotFound::new);
    }

    public List<ChessGame> getUsersGameInfo(String name) {
        return userRepository.findUserPlayers(name).stream().map(Player::getGame).collect(Collectors.toList());
    }

}
