package ua.jupiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.User;
import ua.jupiter.database.entity.UserSubscription;
import ua.jupiter.database.repository.UserDetailsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserDetailsRepository userDetailsRepository;

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                ).toList();

        if (subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        return userDetailsRepository.save(channel);
    }
}