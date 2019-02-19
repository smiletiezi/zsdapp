package com.py.zsdApp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdOrderMapper;
import com.py.zsdApp.entity.ZsdOrder;

@Service
public class ZsdOrderService {

	@Autowired
	private ZsdOrderMapper zsdOrderMapper;
	
	/**
	 * 新增订单
	 */
	public int insertSelective(ZsdOrder zsdOrder) {
		return zsdOrderMapper.insert(zsdOrder);
	}
	/**
	 * 根据id查询订单
	 */
	public ZsdOrder selectByPrimaryKey(Integer id) {
		return zsdOrderMapper.selectByPrimaryKey(id);
	}
	/**
	 * 删除订单
	 */
	public int deleteByPrimaryKey(Integer id) {
		return zsdOrderMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 根据订单类型和用户id获取订单
	 */
	public List<ZsdOrder> selectOrder(ZsdOrder zsdOrder) {
		return zsdOrderMapper.selectOrder(zsdOrder);
	}
	
	/**
	 * 根据经纬度查询附近订单
	 */
	public List<ZsdOrder> selectByLog(Map<String,Object> map){
		return zsdOrderMapper.selectByLog(map);                            
	}
}
