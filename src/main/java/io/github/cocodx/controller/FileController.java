package io.github.cocodx.controller;

import io.github.cocodx.entity.TFiles;
import io.github.cocodx.entity.User;
import io.github.cocodx.service.TFilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author amazfit
 * @date 2022-08-22 上午6:23
 *
 * https://blog.csdn.net/weixin_41866607/article/details/107634879
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
                .setType(type).setPath("/files/"+dataFormat).setUserId(user.getId()).setDownCount(0);
        tFilesService.save(tFiles);

        return "redirect:/file/findAll";
    }

    @GetMapping("/download")
    public void download(String openStyle,Long id, HttpServletResponse response) throws IOException {
        //打开方式
        openStyle = openStyle==null? "attachment":openStyle;

        TFiles tFiles = tFilesService.selectById(id);

        if ("attachment".equals(openStyle)){
            tFiles.setDownCount(tFiles.getDownCount()+1);
            tFilesService.update(tFiles);
        }

        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + tFiles.getPath();

        FileInputStream fileInputStream = new FileInputStream(new File(realPath, tFiles.getNewFileName()));
        //附件下载
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(tFiles.getOldFileName(),"UTF-8"));
        //获取响应输出流
        ServletOutputStream servletOutputStream = response.getOutputStream();
        IOUtils.copy(fileInputStream,servletOutputStream);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(servletOutputStream);
    }

    @GetMapping("/delete")
    public String delete(Long id)throws IOException{
        TFiles tFiles = tFilesService.selectById(id);
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + tFiles.getPath();
        File file = new File(realPath, tFiles.getNewFileName());
        if (file.exists()){
            file.delete();
        }
        tFilesService.delete(id);
        return "redirect:/file/findAll";
    }
}
