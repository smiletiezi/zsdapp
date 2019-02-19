package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdAddressMapper;
import com.py.zsdApp.entity.ZsdAddress;
@Service
public class ZsdAddressService {

	@Autowired
	private ZsdAddressMapper zsdAddressMapper;
	/**
	 * 地址新增
	 */
	public int insertSelective(ZsdAddress address) {
		return zsdAddressMapper.insert(address);
	}
	/**
	 * 地址删除
	 */
	public int deleteByPrimaryKey(Integer addressid) {
		return zsdAddressMapper.deleteByPrimaryKey(addressid);
	}
	/**
	 * 地址修改
	 */
	public int updateByPrimaryKeySelective(ZsdAddress address) {
		return zsdAddressMapper.updateByPrimaryKeySelective(address);
	}
	/**
	 * 根据id获取单个地址
	 */
	public ZsdAddress selectByPrimaryKey(Integer address) {
		return zsdAddressMapper.selectByPrimaryKey(address);
	}
	/**
	 * 根据userid获取多个地址
	 */
	public List<ZsdAddress> selectByUserid(Integer userid) {
		return zsdAddressMapper.selectByUserid(userid);
	}
}
