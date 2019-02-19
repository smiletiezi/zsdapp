package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdSigninrecord;

public interface ZsdSigninrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdSigninrecord record);

    int insertSelective(ZsdSigninrecord record);

    ZsdSigninrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdSigninrecord record);

    int updateByPrimaryKey(ZsdSigninrecord record);
    
    List<ZsdSigninrecord> selectByUserid(Integer userid);
}