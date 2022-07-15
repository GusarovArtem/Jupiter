package ua.jupiter.service.implementation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.repository.UserRepository;
import ua.jupiter.service.interfaces.UserService;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User create(User user) {

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findById(String id) {

        return userRepository.findById(id).orElse(null);
    }
}