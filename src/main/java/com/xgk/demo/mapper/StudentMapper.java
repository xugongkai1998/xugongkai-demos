package com.xgk.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: xugongkai
 * @created: 2022-02-08 18:03
 */
@Mapper
public interface StudentMapper {

    @Select("SELECT COUNT(1) FROM test.student;")
    int countStudent();

}
