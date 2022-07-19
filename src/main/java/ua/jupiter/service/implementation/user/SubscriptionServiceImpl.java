package ua.jupiter.service.implementation.user;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;
import ua.jupiter.database.repository.UserSubscriptionRepository;
import ua.jupiter.service.decorator.CashedUserService;
import ua.jupiter.service.interfaces.UserSubscriptionService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubscriptionServiceImpl implements UserSubscriptionService {

    CashedUserService userService;

    UserSubscriptionRepository userSubscriptionRepository;

    @Transactional
    @Override
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

        return userService.saveSubscription(channel, subscriber);
    }

    @Override
    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepository.findByChannel(channel);
    }

    @Transactional
    @Override
    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        System.out.println();
        UserSubscription subscription = userSubscriptionRepository
                .findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepository.save(subscription);
    }
}