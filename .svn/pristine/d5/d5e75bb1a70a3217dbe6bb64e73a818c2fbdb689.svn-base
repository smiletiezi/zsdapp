package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdMaillistMapper;
import com.py.zsdApp.entity.ZsdMaillist;

@Service
public class ZsdMaillistService {
	@Autowired
	private ZsdMaillistMapper zsdMaillistMapper;
	/**
	 * 新增好友
	 */
	public int insertSelective(ZsdMaillist zsdMaillist) {
		return zsdMaillistMapper.insert(zsdMaillist);
	}
	/**
	 * 删除好友
	 */
	public int deleteByPrimaryKey(Integer id) {
		return zsdMaillistMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 修改好友备注
	 */
	public int updateByPrimaryKeySelective(ZsdMaillist zsdMaillist) {
		return zsdMaillistMapper.updateByPrimaryKeySelective(zsdMaillist);
	}
	/**
	 * 根据id获取单个好友
	 */
	public ZsdMaillist selectByPrimaryKey(Integer mailliid) {
		return zsdMaillistMapper.selectByPrimaryKey(mailliid);
	}
	/**
	 * 根据userid获取多个好友
	 */
	public List<ZsdMaillist> selectByfirend(ZsdMaillist zsdMaillist) {
		return zsdMaillistMapper.selectByfirend(zsdMaillist);
	}
}
