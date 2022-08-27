package io.github.cocodx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author amazfit
 * @date 2022-08-22 上午6:25
 **/
@Controller
public class IndexController {

    /**
     * 跳转登录页
     * @return
     */
    @GetMapping("/page/login")
    public String toLogin(){
        return "login";
    }
}
