package ua.jupiter.database.listener;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.jupiter.database.entity.user.OAuth2User;
import ua.jupiter.database.entity.Revision;

public class UserRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {
        Revision revisionEntity = (Revision) o;
        OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        revisionEntity.setUserId(user.getName());
        revisionEntity.setUsername(user.getUserName());
    }
}