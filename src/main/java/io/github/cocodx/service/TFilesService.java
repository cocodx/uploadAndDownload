package io.github.cocodx.service;

import io.github.cocodx.entity.TFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFilesService {


    List<TFiles> selectListByUserId(Long userId);
}
