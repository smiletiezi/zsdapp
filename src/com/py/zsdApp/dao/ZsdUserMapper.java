package com.py.zsdApp.dao;

import java.util.List;
import java.util.Map;

import com.py.zsdApp.entity.ZsdUser;

public interface ZsdUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(ZsdUser record);

    int insertSelective(ZsdUser record);

    ZsdUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(ZsdUser record);

    int updateByPrimaryKey(ZsdUser record);
    //根据用户属性 查询用户列表 
    ZsdUser selectByUser(ZsdUser record);
    
    List<ZsdUser> selectByUserIndustry(ZsdUser record);
    
    //根据经纬度查询附近
    List<ZsdUser> selectByLog(Map<String,Object> map);
}