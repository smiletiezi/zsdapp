package com.py.zsdApp.dao;

import com.py.zsdApp.entity.ZsdIntegral;

public interface ZsdIntegralMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdIntegral record);

    int insertSelective(ZsdIntegral record);

    ZsdIntegral selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdIntegral record);

    int updateByPrimaryKey(ZsdIntegral record);
    
    ZsdIntegral selectByUserid(Integer userid);
}