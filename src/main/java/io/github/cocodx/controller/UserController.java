package io.github.cocodx.controller;

import io.github.cocodx.entity.User;
import io.github.cocodx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author amazfit
 * @date 2022-08-22 上午6:21
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(User user, HttpSession httpSession){
        User login = userService.login(user);
        if (login!=null){
            httpSession.setAttribute("user",login);
            return "redirect:/file/findAll";
        }
        return "redirect:/login";
    }
}
