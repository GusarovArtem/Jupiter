package ua.jupiter.service.interfaces;


import ua.jupiter.database.entity.user.User;
import ua.jupiter.dto.read.UserReadDto;

public interface UserService {

    User getUser(UserReadDto userReadDto);

    User create(User user);

}