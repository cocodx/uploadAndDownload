package io.github.cocodx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author amazfit
 * @date 2022-08-22 上午6:23
 **/
@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/findAll")
    public String findAll(){
        System.out.println("查询所有进入...........");
        return "showAll";
    }
}
