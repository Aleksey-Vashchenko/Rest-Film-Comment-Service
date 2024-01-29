package com.vashchenko.restfilmcommentservice.v1.entities.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {

    @Size(min = 8, message = "Логин должен содержать не менее 8 символов")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$",
            message = "Логин должен содержать хотя бы одну заглавную латинскую букву и хотя бы одну цифру, и разрешены только латинские буквы и цифры"
    )
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Size(min = 8, message = "Логин должен содержать не менее 8 символов")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!_]+$",
            message = "Логин должен содержать хотя бы одну заглавную латинскую букву и хотя бы одну цифру, разрешены только латинские буквы, цифры, восклицательный знак и нижнее подчеркивание"
    )
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
