package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.jupiter.database.entity.User;
import ua.jupiter.database.entity.UserSubscription;
import ua.jupiter.database.entity.UserSubscriptionId;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
