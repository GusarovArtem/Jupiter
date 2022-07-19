package ua.jupiter.service.decorator;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;
import ua.jupiter.service.interfaces.UserSubscriptionService;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LoggedSubscriptionService implements UserSubscriptionService {

    UserSubscriptionService subscriptionServiceImpl;


    @Override
    public User changeSubscription(User channel, User subscriber) {

        log.info("Change subscription: ");
        log.info("Channel id: " + channel.getId());
        log.info("Subscriber text: " + subscriber.getId());

        return subscriptionServiceImpl.changeSubscription(channel, subscriber);
    }

    @Override
    public List<UserSubscription> getSubscribers(User channel) {

        log.info("Get subscribers: ");
        log.info("Channel id: " + channel.getId());
        log.info("Channel name: " + channel.getName());

        return subscriptionServiceImpl.getSubscribers(channel);
    }

    @Override
    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {

        log.info("Change subscription status: ");
        log.info("Channel id: " + channel.getId());
        log.info("Subscriber text: " + subscriber.getId());

        return subscriptionServiceImpl.changeSubscriptionStatus(channel, subscriber);
    }
}