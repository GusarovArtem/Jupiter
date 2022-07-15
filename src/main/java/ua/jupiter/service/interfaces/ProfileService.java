package ua.jupiter.service.interfaces;

import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;

import java.util.List;

@Service
public interface ProfileService {

    User changeSubscription(User channel, User subscriber);

    List<UserSubscription> getSubscribers(User channel);

    UserSubscription changeSubscriptionStatus(User channel, User subscriber);
}
