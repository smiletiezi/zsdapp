package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdMamber;

public interface ZsdMamberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdMamber record);

    int insertSelective(ZsdMamber record);

    ZsdMamber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdMamber record);

    int updateByPrimaryKey(ZsdMamber record);
    
    List<ZsdMamber> selectByExample(ZsdMamber record);
}