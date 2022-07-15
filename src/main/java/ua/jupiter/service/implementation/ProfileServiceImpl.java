package ua.jupiter.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;
import ua.jupiter.database.repository.SubscriptionRepository;
import ua.jupiter.database.repository.UserRepository;
import ua.jupiter.service.interfaces.ProfileService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

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
            subscriptions.forEach(channel.getSubscribers()::remove);
        }

        return userRepository.save(channel);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return subscriptionRepository.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = subscriptionRepository.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return subscriptionRepository.save(subscription);
    }
}
