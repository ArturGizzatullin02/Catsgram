package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User getUser(Long id)  {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с ID: " + id + " не найден");
        }
        return users.get(id);
    }

    public User addUser(User user) {
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

    public User updateUser(User user) {
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
