package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdIntegralDetailsMapper;
import com.py.zsdApp.entity.ZsdIntegralDetails;

@Service
public class ZsdIntegralDetailsService {
	@Autowired
	private ZsdIntegralDetailsMapper zsdIntegralDetailsMapper;
	/**
	 * 新增积分明细
	 */
	public int insertSelective(ZsdIntegralDetails zsdIntegralDetails) {
		return zsdIntegralDetailsMapper.insert(zsdIntegralDetails);
	}
	/**
	 * 根据userid获取多条积分明细
	 */
	public List<ZsdIntegralDetails> selectByZsdIntegralDetails(ZsdIntegralDetails zsdIntegralDetails) {
		return zsdIntegralDetailsMapper.selectByZsdIntegralDetails(zsdIntegralDetails);
	}
}
