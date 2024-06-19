package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;

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
    public Post findById(@PathVariable Long id)  {
        return postService.findById(id);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost)  {
        return postService.update(newPost);
    }

}