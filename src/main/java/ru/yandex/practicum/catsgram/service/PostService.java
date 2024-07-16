package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.Instant;
import java.util.*;

@Service
public class PostService {
    private final Map<Long, Post> posts = new HashMap<>();
    long maxId = 0;

    public Collection<Post> findAll(Long from, Long size, String sort) {
        List<Post> filteredPosts = new ArrayList<>();
        if (from == -1) {
            if (size > posts.size()) {
                for (long i = 1; i <= posts.size(); i++) {
                    filteredPosts.add(posts.get(i));
                }
            } else if (size == 10) {
                for (long i = posts.size() - 9; i <= posts.size(); i++) {
                    filteredPosts.add(posts.get(i));
                }
            } else {
                for (long i = posts.size() - size; i <= posts.size(); i++) {
                    filteredPosts.add(posts.get(i));
                }
            }
        } else if ((from + size) < posts.size()) {
            for (long i = from + 1; i <= from + size; i++) {
                filteredPosts.add(posts.get(i));
            }
        } else {
            for (long i = from + 1; i <= posts.size(); i++) {
                filteredPosts.add(posts.get(i));
            }
        }
        if (sort.equals("asc")) {
            Comparator<Post> byCreationDate = Comparator.comparing(Post::getPostDate).reversed();
            filteredPosts.sort(byCreationDate);
        } else if (sort.equals("desc")) {
            Comparator<Post> byCreationDate = Comparator.comparing(Post::getPostDate);
            filteredPosts.sort(byCreationDate);
        }
        return filteredPosts;
    }

    public Optional<Post> findById(Long id)  {
        if (!posts.containsKey(id)) {
            throw new NotFoundException("Пост с id " + id + " не найден");
        }
        return Optional.of(posts.get(id));
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }
        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    private long getNextId() {
        return ++maxId;
    }

    public Post update(Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (posts.containsKey(newPost.getId())) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return oldPost;
        }
        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
    }
}
