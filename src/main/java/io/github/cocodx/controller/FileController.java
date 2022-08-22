package io.github.cocodx.controller;

import io.github.cocodx.entity.TFiles;
import io.github.cocodx.entity.User;
import io.github.cocodx.service.TFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        User user = (User) session.getAttribute("user");
        List<TFiles> tFiles = tFilesService.selectListByUserId(user.getId());
        model.addAttribute("files",tFiles);
        return "showAll";
    }

    @PostMapping("/upload")
    public String upload(MultipartFile aaa,HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        //获取原始文件名称
        String oldFileName = aaa.getOriginalFilename();

        String extension = "."+oldFileName.split("\\.")[1];

        String newFileName = UUID.randomUUID().toString().replace("-","")+extension;

        //大小
        Long size = aaa.getSize();
        //文件类型

        String type = aaa.getContentType();

        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static/files";
        String dataFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dataDirPath = realPath+"/"+dataFormat;
        File dateDir = new File(dataDirPath);
        if (!dateDir.exists()){
            dateDir.mkdirs();
        }

        aaa.transferTo(new File(dateDir,newFileName));

        TFiles tFiles = new TFiles();
        tFiles.setOldFileName(oldFileName).setNewFileName(newFileName).setExt(extension).setSize(String.valueOf(size))
                .setType(type).setPath("/files/"+dataFormat).setUserId(user.getId());
        tFilesService.save(tFiles);

        return "redirect:/file/findAll";
    }
}
