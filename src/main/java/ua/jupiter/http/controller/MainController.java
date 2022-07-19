package ua.jupiter.http.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.jupiter.http.dto.read.UserReadDto;
import ua.jupiter.database.entity.user.OAuth2User;
import ua.jupiter.database.entity.message.Message;
import ua.jupiter.service.implementation.message.MessageServiceImpl;
import ua.jupiter.service.implementation.user.UserServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    @NonFinal
    @Value("${spring.profiles.active:prod}")
    String profile;

    UserServiceImpl userService;

    MessageServiceImpl messageService;


    @Transactional
    @GetMapping
    public String index(@AuthenticationPrincipal OAuth2User oauthUser,
                        Model model) {
        Map<Object, Object> data = new HashMap<>();

        if (oauthUser != null) {
            UserReadDto userEntity = userService.getOauthUser(oauthUser.getName());
            data.put("profile", userEntity);
            List<Message> messages = messageService.findForUser(oauthUser.getUser());
            data.put("profile", userEntity);
            data.put("messages", messages);
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}