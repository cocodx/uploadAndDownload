package io.github.cocodx.controller;

import io.github.cocodx.entity.TFiles;
import io.github.cocodx.entity.User;
import io.github.cocodx.service.TFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author amazfit
 * @date 2022-08-22 上午6:23
 **/
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private TFilesService tFilesService;

    @GetMapping("/findAll")
    public String findAll(HttpSession session, Model model){
        System.out.println("查询所有进入...........");
        User user = (User) session.getAttribute("user");
        List<TFiles> tFiles = tFilesService.selectListByUserId(user.getId());
        model.addAttribute("files",tFiles);
        return "showAll";
    }
}
