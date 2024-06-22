package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam(defaultValue = "-1") long page, @RequestParam(defaultValue = "10") long size, @RequestParam(defaultValue = "asc") String sort) {
        return postService.findAll(page, size, sort);
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id)  {
        return postService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Optional<Post>> create(@RequestBody Post post) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Optional.of(postService.create(post)));
    }

    @PutMapping
    public Post update(@RequestBody Post newPost)  {
        return postService.update(newPost);
    }

}