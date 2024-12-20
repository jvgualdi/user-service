package tec.jvgualdi.user_service.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tec.jvgualdi.user_service.dto.UserListing;
import tec.jvgualdi.user_service.dto.UserRequest;
import tec.jvgualdi.user_service.entity.User;
import tec.jvgualdi.user_service.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User createUser(UserRequest user) {
        return userRepository.save(new User(user));
    }

    public List<UserListing> getAllUsers() {
        return userRepository.findAll().stream().map(UserListing::new).toList();
    }
}
