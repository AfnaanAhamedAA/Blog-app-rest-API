package com.myblogrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotNull
    @Size(min = 2, message = "Title cannot be less than 2 characters")
    private String title;
    private String description;
    private String content;
}
