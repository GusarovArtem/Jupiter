package ua.jupiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.User;
import ua.jupiter.database.repository.UserDetailsRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserDetailsRepository userDetailsRepository;

    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        return userDetailsRepository.save(channel);
    }
}