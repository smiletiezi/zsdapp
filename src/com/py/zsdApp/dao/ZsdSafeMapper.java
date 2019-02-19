package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdSafe;

public interface ZsdSafeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdSafe record);

    int insertSelective(ZsdSafe record);

    ZsdSafe selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdSafe record);

    int updateByPrimaryKey(ZsdSafe record);
    
    List<ZsdSafe> selectByExample(ZsdSafe record);
}