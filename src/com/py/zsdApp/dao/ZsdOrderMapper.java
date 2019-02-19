package com.py.zsdApp.dao;

import java.util.List;
import java.util.Map;

import com.py.zsdApp.entity.ZsdOrder;

public interface ZsdOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdOrder record);

    int insertSelective(ZsdOrder record);

    ZsdOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdOrder record);

    int updateByPrimaryKeyWithBLOBs(ZsdOrder record);

    int updateByPrimaryKey(ZsdOrder record);
    
    List<ZsdOrder> selectOrder(ZsdOrder record);
    
    //根据经纬度查询附近订单
    List<ZsdOrder> selectByLog(Map<String,Object> map);
}