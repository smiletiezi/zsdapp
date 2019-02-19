package com.py.zsdApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdSigninMapper;
import com.py.zsdApp.entity.ZsdSignin;

@Service
public class ZsdSigninService {

	@Autowired
	private ZsdSigninMapper zsdSigninMapper;
	/**
	 * 签到新增
	 */
	public int insertSelective(ZsdSignin zsdSignin) {
		return zsdSigninMapper.insert(zsdSignin);
	}
	/**
	 * 签到修改
	 */
	public int updateByPrimaryKeySelective(ZsdSignin zsdSignin) {
		return zsdSigninMapper.updateByPrimaryKeySelective(zsdSignin);
	}
	/**
	 * 根据用户id获取签到
	 */
	public ZsdSignin selectByUserid(Integer userid) {
		return zsdSigninMapper.selectByUserid(userid);
	}
}
