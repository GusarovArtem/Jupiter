package ua.jupiter.service.interfaces;


import org.springframework.stereotype.Service;
import ua.jupiter.api.dto.read.ProfileReadDto;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.api.dto.read.UserReadDto;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findById(String id);

    User getById(String id);

    UserReadDto getOauthUser(String id);

    ProfileReadDto getProfile(String userId);

    List<UserReadDto> getAllUsers();

    User create(User user);

}