package ua.jupiter.service.implementation.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.http.dto.read.user.ProfileReadDto;
import ua.jupiter.http.dto.read.user.UserReadDto;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.repository.UserRepository;
import ua.jupiter.service.interfaces.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    ModelMapper modelMapper;

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserReadDto getOauthUser(String id) {

        return modelMapper.map(id, UserReadDto.class);
    }

    @Override
    public ProfileReadDto getProfile(String userId) {
        return modelMapper.map(userId, ProfileReadDto.class);
    }

    @Override
    public List<UserReadDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserReadDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }
}