package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdMaillist;

public interface ZsdMaillistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdMaillist record);

    int insertSelective(ZsdMaillist record);

    ZsdMaillist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdMaillist record);

    int updateByPrimaryKey(ZsdMaillist record);
    
    List<ZsdMaillist> selectByfirend(ZsdMaillist zsdMaillist);
}