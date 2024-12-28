package tec.jvgualdi.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tec.jvgualdi.user_service.dto.UserDetails;
import tec.jvgualdi.user_service.dto.UserRequest;
import tec.jvgualdi.user_service.entity.User;
import tec.jvgualdi.user_service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails createUser(UserRequest user) {
        var userEntity = userRepository.findByEmail(user.email());
        if (userEntity.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        userEntity = userRepository.findByUsername(user.username());
        if (userEntity.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        return new UserDetails(userRepository.save(new User(user)));
    }

    public Page<UserDetails> getAllUsers(Pageable pageableUser) {
        return userRepository.findAllByActiveTrue(pageableUser).map(UserDetails::new);
    }

    public UserDetails getUserDetails(Long userId) {
        return new UserDetails(userRepository.getReferenceById(userId));
    }

    public void deleteUser(Long userId){
        var user = userRepository.getReferenceById(userId);
        user.setActive(false);
    }


}
