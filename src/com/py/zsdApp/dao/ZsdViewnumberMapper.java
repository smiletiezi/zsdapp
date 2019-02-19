package com.py.zsdApp.dao;

import com.py.zsdApp.entity.ZsdViewnumber;

public interface ZsdViewnumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdViewnumber record);

    int insertSelective(ZsdViewnumber record);

    ZsdViewnumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdViewnumber record);

    int updateByPrimaryKey(ZsdViewnumber record);
}