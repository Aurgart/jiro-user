package java_jabi.user_jiro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java_jabi.user_jiro.model.User;
import java_jabi.user_jiro.model.UserData;
import java_jabi.user_jiro.model.UserInfo;
import java_jabi.user_jiro.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать сотрудника")
    public UserInfo create(@RequestBody UserData user){
        return userService.addUser(user);
    }

    @DeleteMapping("/del_user/{id}")
    @Operation(summary = "Удалить сотрудника")
    public void create(@PathVariable(required = true) Long id){
        userService.delete(id);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Получить работающего пользователя")
    public UserInfo getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/hist_user/{id}")
    @Operation(summary = "Получить пользователя (включая удаленных)")
    public UserInfo GetUser(@PathVariable("id") Long id) {
        return  userService.getUser(id);
    }
    @GetMapping("/user/check/{id}")
    @Operation(summary = "Проверка пользователя.")
    public Boolean chById(@PathVariable("id") Long id) {
        return  userService.checkUser(id);
    }
    @GetMapping("/hist_user/check/{id}")
    @Operation(summary = "Проверка пользователя (включая удаленных)")
    public Boolean chHist(@PathVariable("id") Long id) {
        return  userService.checkHistUser(id);
    }
}
