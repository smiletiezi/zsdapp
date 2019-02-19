package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdNews;

public interface ZsdNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdNews record);

    int insertSelective(ZsdNews record);

    ZsdNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdNews record);

    int updateByPrimaryKey(ZsdNews record);
    
    List<ZsdNews> selectByTitle(ZsdNews record);
}