package io.github.cocodx.service.impl;

import io.github.cocodx.dao.TFilesDao;
import io.github.cocodx.entity.TFiles;
import io.github.cocodx.service.TFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TFilesServiceImpl implements TFilesService {

    @Autowired
    private TFilesDao filesDao;

    @Override
    public List<TFiles> selectListByUserId(Long userId) {
        return filesDao.selectListByUserId(userId);
    }

    @Override
    public void save(TFiles tFiles) {
        String isImg = tFiles.getType().startsWith("image")? "yes":"no";
        tFiles.setIsImg(isImg);
        filesDao.save(tFiles);
    }

    @Override
    public TFiles selectById(Long id) {
        return filesDao.selectById(id);
    }

    @Override
    public void update(TFiles tFiles) {
        filesDao.update(tFiles);
    }
}
