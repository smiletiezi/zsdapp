package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdSigninrecordMapper;
import com.py.zsdApp.entity.ZsdSigninrecord;

@Service
public class ZsdSigninrecordService {

	@Autowired
	private ZsdSigninrecordMapper zsdSigninrecordMapper;
	/**
	 * 用户新增
	 */
	public int insertSelective(ZsdSigninrecord zsdSigninrecord) {
		return zsdSigninrecordMapper.insert(zsdSigninrecord);
	}
	/**
	 * 根据用户id获取签到
	 */
	public List<ZsdSigninrecord> selectByUserid(Integer userid) {
		return zsdSigninrecordMapper.selectByUserid(userid);
	}
}
