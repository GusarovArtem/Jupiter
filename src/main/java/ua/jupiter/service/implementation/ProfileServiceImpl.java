package ua.jupiter.service.implementation;

import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;
import ua.jupiter.service.interfaces.ProfileService;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Override
    public User changeSubscription(User channel, User subscriber) {
        return null;
    }

    @Override
    public List<UserSubscription> getSubscribers(User channel) {
        return null;
    }

    @Override
    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        return null;
    }
}
