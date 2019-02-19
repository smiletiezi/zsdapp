package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdOrderType;

public interface ZsdOrderTypeMapper {
    int deleteByPrimaryKey(Integer id);

	int insert(ZsdOrderType record);

	int insertSelective(ZsdOrderType record);

	ZsdOrderType selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ZsdOrderType record);

	int updateByPrimaryKey(ZsdOrderType record);
    
    List<ZsdOrderType> selectAll();
}