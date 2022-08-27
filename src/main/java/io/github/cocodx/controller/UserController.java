package io.github.cocodx.controller;

import io.github.cocodx.entity.User;
import io.github.cocodx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

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
            //验证码
            String code = (String) httpSession.getAttribute("code");
            if (!code.equalsIgnoreCase(user.getCode())){
                return "redirect:/page/login";
            }

            httpSession.setAttribute("user",login);
            return "redirect:/file/findAll";
        }
        return "redirect:/page/login";
    }

    @GetMapping("/code")
    public void code(HttpServletResponse response,HttpSession session) throws IOException {
        //创建空白图片
        BufferedImage bufferedImage = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
        //获取图片画笔
        Graphics graphics = bufferedImage.getGraphics();
        Random random = new Random();
        //设置画笔颜色
        graphics.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        //绘制矩形背景
        graphics.fillRect(0,0,100,30);
        //绘制8条干扰线
        for (int i = 0; i < 8; i++) {
            graphics.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            graphics.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));
        }
        String codeStr = randomStr(5);
        //存到session里面
        session.setAttribute("code",codeStr);

        graphics.setFont(new Font(null,Font.ITALIC+Font.BOLD,24));
        //当xy为0，在左下角
        graphics.drawString(codeStr,5,25);

        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage,"jpeg",outputStream);
        outputStream.close();
    }

    public static String randomStr(int size){
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            char c = str.charAt(random.nextInt(str.length()-1));
            buffer.append(c);
        }
        return buffer.toString();
    }


    public static void main(String[] args) {
        System.out.println(randomStr(4));
    }
}
