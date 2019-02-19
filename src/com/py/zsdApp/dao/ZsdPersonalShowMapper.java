package com.py.zsdApp.dao;

import com.py.zsdApp.entity.ZsdPersonalShow;

public interface ZsdPersonalShowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdPersonalShow record);

    int insertSelective(ZsdPersonalShow record);

    ZsdPersonalShow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdPersonalShow record);

    int updateByPrimaryKey(ZsdPersonalShow record);
}