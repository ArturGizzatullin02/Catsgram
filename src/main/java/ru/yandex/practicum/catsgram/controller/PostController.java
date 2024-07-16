package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
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
    public Collection<Post> findAll(@RequestParam(defaultValue = "-1") Long from,
                                    @RequestParam(defaultValue = "10") Long size,
                                    @RequestParam(defaultValue = "asc") String sort) {
        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new ParameterNotValidException(sort, "Параметр sort должен быть 'asc' или 'desc'");
        }
        if (from < 0)  {
            throw new ParameterNotValidException(from.toString(), "Параметр from должен быть неотрицательным");
        }
        if (size  <=0) {
            throw new ParameterNotValidException(size.toString(), "Параметр size должен быть больше нуля");
        }
        return postService.findAll(from, size, sort);
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