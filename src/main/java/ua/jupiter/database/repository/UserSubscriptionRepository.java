package ua.jupiter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.jupiter.database.entity.user.User;
import ua.jupiter.database.entity.user.UserSubscription;
import ua.jupiter.database.entity.user.UserSubscriptionId;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {

    @Query("SELECT chan FROM UserSubscription u " +
            "LEFT JOIN u.subscriber sub " +
            "LEFT JOIN u.channel chan " +
            "WHERE  sub.id = :userId AND u.active = true")
    List<User> findBySubscriber(String userId);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}