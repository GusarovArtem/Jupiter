package ua.jupiter.service.interfaces;


import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.user.User;

@Service
public interface UserService {

    User create(User user);

    User findById(String id);
}