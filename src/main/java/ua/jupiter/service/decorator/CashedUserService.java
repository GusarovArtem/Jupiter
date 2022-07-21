package ua.jupiter.service.decorator;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;
import ua.jupiter.api.dto.read.user.ProfileReadDto;
import ua.jupiter.api.dto.read.user.UserReadDto;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.service.implementation.user.UserServiceImpl;
import ua.jupiter.service.interfaces.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CashedUserService implements UserService {

    UserServiceImpl userServiceImpl;

    @NonFinal
    final ConcurrentHashMap<String, User> cashedUsers = new ConcurrentHashMap<>();


    @Override
    public Optional<User> findById(String id) {

        if (cashedUsers.containsKey(id)) {
            return Optional.ofNullable(cashedUsers.get(id));
        }

        Optional<User> user = userServiceImpl.findById(id);
        user.ifPresent( u -> cashedUsers.put(u.getId(), u));

        return user;
    }

    @Override
    public User getById(String id) {

        return cashedUsers.compute(id, (k, v) ->
                Objects.isNull(v) ? userServiceImpl.getById(id): v
        );
    }

    @Override
    public UserReadDto getOauthUser(String id) {
        return userServiceImpl.getOauthUser(id);
    }

    @Override
    public ProfileReadDto getProfile(String userId) {
        return userServiceImpl.getProfile(userId);
    }

    @Override
    public List<UserReadDto> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @Override
    public User createUser(User user) {
        userServiceImpl.createUser(user);
        cashedUsers.put(user.getId(), user);
        return user;
    }


    public User saveSubscription(User channel, User subscriber) {
        cashedUsers.remove(subscriber.getId());

        User savedChannel = userServiceImpl.createUser(channel);
        cashedUsers.put(savedChannel.getId(), savedChannel);

        return savedChannel;
    }
}