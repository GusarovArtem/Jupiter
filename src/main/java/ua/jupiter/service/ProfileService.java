package ua.jupiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.User;
import ua.jupiter.database.entity.UserSubscription;
import ua.jupiter.database.repository.UserDetailsRepository;
import ua.jupiter.database.repository.UserSubscriptionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserDetailsRepository userDetailsRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                ).collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            subscriptions.forEach(channel.getSubscribers()::remove);
        }

        return userDetailsRepository.save(channel);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepository.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionRepository.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepository.save(subscription);
    }
}