package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdArea;

public interface ZsdAreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdArea record);

    int insertSelective(ZsdArea record);

    ZsdArea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdArea record);

    int updateByPrimaryKey(ZsdArea record);
    
    List<ZsdArea> selectByArea(ZsdArea area);
}