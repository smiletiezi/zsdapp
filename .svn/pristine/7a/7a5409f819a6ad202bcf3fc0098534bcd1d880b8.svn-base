package com.py.zsdApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdIntegralMapper;
import com.py.zsdApp.entity.ZsdIntegral;
@Service
public class ZsdIntegralService {

	@Autowired
	private ZsdIntegralMapper zsdIntegralMapper;
	
	/**
	 * 新增积分表
	 */
	public int insertSelective(ZsdIntegral zsdIntegral) {
		return zsdIntegralMapper.insert(zsdIntegral);
	}
	/**
	 * 修改积分表
	 */
	public int updateByPrimaryKeySelective(ZsdIntegral zsdIntegral) {
		return zsdIntegralMapper.updateByPrimaryKeySelective(zsdIntegral);
	}
	/**
	 * 根据userid获取积分表
	 */
	public ZsdIntegral selectByUserid(Integer userid) {
		return zsdIntegralMapper.selectByUserid(userid);
	}
}
