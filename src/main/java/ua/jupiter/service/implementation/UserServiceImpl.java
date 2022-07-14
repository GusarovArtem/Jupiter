package ua.jupiter.service.implementation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.repository.UserRepository;
import ua.jupiter.dto.read.UserReadDto;
import ua.jupiter.service.interfaces.UserService;

import java.lang.reflect.Type;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUser(UserReadDto userReadDto) {

        final User user = userRepository.findById(userReadDto.getId())
                .orElseGet(() -> modelMapper.map(userReadDto, (Type) UserReadDto.class));

        return create(user);
    }

    @Transactional
    @Override
    public User create(User user) {

        return userRepository.saveAndFlush(user);
    }
}