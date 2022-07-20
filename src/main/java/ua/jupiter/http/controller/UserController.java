package ua.jupiter.http.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.jupiter.http.dto.read.user.UserReadDto;
import ua.jupiter.database.entity.user.OAuth2User;
import ua.jupiter.service.implementation.user.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {

    UserServiceImpl userService;

    @GetMapping
    public List<UserReadDto> getAllUsers(@AuthenticationPrincipal OAuth2User oauthUser){
        return userService.getAllUsers().stream()
                .filter(user -> !user.getId().equals(oauthUser.getName()))
                .collect(Collectors.toList());
    }

}