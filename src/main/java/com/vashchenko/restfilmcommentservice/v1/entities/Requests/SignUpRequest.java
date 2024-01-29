package com.vashchenko.restfilmcommentservice.v1.entities.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {

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
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;


}