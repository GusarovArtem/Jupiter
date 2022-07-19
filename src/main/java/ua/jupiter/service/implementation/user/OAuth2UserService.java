package ua.jupiter.service.implementation.user;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ua.jupiter.database.entity.user.OAuth2User;
import ua.jupiter.database.entity.user.User;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OAuth2UserService extends OidcUserService {

    UserServiceImpl userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        User user = saveUser(oidcUser);
        return new OAuth2User(oidcUser, user);
    }


    private User saveUser(OidcUser oidcUser){

        Map<String, Object> attributes = oidcUser.getAttributes();
        String id = (String) attributes.get("sub");

        User user = userService.findById(id).orElseGet(() ->
                User.builder()
                        .id(id)
                        .name((String) attributes.get("name"))
                        .email((String) attributes.get("email"))
                        .locale((String) attributes.get("locale"))
                        .userPicture((String) attributes.get("picture"))
                        .build());

        user.setLastVisit(LocalDateTime.now());

        userService.createUser(user);

        return user;
    }


}