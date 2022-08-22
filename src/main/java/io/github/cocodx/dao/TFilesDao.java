package io.github.cocodx.dao;

import io.github.cocodx.entity.TFiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TFilesDao {

    List<TFiles> selectListByUserId(@Param("userId") Long userId);

    void save(TFiles tFiles);
}
