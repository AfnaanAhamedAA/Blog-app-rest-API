package com.myblogrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private  long id;
    private String body;
    @NotNull
    @Email(message = "Email format invalid")
    private String email;
    private String name;

}
