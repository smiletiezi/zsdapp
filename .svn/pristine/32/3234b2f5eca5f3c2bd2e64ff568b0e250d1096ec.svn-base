package com.py.zsdApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdAboutUsMapper;
import com.py.zsdApp.entity.ZsdAboutUs;

@Service
public class ZsdAboutUsService {

	@Autowired
	private ZsdAboutUsMapper zsdAboutUsMapper;
	/**
	 * 根据id获取关于我们
	 */
	public ZsdAboutUs selectByPrimaryKey(Integer aboutusid) {
		return zsdAboutUsMapper.selectByPrimaryKey(aboutusid);
	}
}
