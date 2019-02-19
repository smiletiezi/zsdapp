package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdInsurance;

public interface ZsdInsuranceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdInsurance record);

    int insertSelective(ZsdInsurance record);

    ZsdInsurance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdInsurance record);

    int updateByPrimaryKey(ZsdInsurance record);
    
    List<ZsdInsurance> selectByExample(ZsdInsurance record);
}