package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdOrderTypeMapper;
import com.py.zsdApp.entity.ZsdOrderType;

@Service
public class ZsdOrderTypeService {

	@Autowired
	private ZsdOrderTypeMapper zsdOrderTypeMapper;
	
	
	public ZsdOrderType selectByPrimaryKey(Integer id) {
		return zsdOrderTypeMapper.selectByPrimaryKey(id);
	}
	/**
	 *获取订单类型
	 */
	public List<ZsdOrderType> selectAll() {
		return zsdOrderTypeMapper.selectAll();
	}
}
