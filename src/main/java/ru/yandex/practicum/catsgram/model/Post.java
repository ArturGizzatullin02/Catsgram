package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Data
public class Post {
    Long id;
    long authorId;
    String description;
    Instant postDate;
}
