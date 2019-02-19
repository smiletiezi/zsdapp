package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdIntegralDetails;

public interface ZsdIntegralDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdIntegralDetails record);

    int insertSelective(ZsdIntegralDetails record);

    ZsdIntegralDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdIntegralDetails record);

    int updateByPrimaryKey(ZsdIntegralDetails record);
    
    List<ZsdIntegralDetails> selectByZsdIntegralDetails(ZsdIntegralDetails zsdIntegralDetails);
}