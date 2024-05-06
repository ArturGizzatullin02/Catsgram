package ru.yandex.practicum.catsgram.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Data
public class Image {
    Long id;
    long postId;
    String originalFileName;
    String filePath;
}
