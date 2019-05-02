package server.service;

import domain.User;
import server.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOne(String username, String password) {
        User user = userRepository.findOne(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
