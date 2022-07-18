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

    @Query("select chan from UserSubscription u " +
            "left join u.subscriber sub " +
            "left join u.channel chan " +
            "where  sub.id = :userId and u.active = true")
    List<User> findBySubscriber(String userId);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}