package ua.jupiter.service.interfaces;

import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;

import java.util.List;

public interface UserSubscriptionService {

    User changeSubscription(User channel, User subscriber);

    List<UserSubscription> getSubscribers(User channel);

    UserSubscription changeSubscriptionStatus(User channel, User subscriber);
}