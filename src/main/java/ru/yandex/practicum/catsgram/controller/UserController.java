package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    Map<Long, User> users = new HashMap<>();
    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (user.getEmail() == null) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else if (emailValidation(user.getEmail())) {
            throw new ConditionsNotMetException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("ID должен быть указан");
        } else if (emailValidation(user.getEmail())) {
            throw new ConditionsNotMetException("Этот имейл уже используется");
        } else if (user.getId() == null) {
            return user;
        } else if (user.getEmail() == null) {
            return user;
        } else if (user.getPassword() == null) {
            return user;
        } else {
            users.put(user.getId(), user);
            return user;
        }
    }

    private boolean emailValidation(String email) {
//        return users.values().stream()
//                .noneMatch(user -> user.getEmail().equals(email));
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private long getNextId() {
        long currentMaxId = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
