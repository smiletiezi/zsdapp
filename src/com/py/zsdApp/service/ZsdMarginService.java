package com.py.zsdApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdMarginMapper;
import com.py.zsdApp.entity.ZsdMargin;

@Service
public class ZsdMarginService {

	@Autowired
	private ZsdMarginMapper zsdMarginMapper;
	/**
	 * 新增保证金
	 */
	public int insertSelective(ZsdMargin zsdMargin) {
		return zsdMarginMapper.insert(zsdMargin);
	}
	
	public ZsdMargin selectMargin(ZsdMargin zsdMargin) {
		return zsdMarginMapper.selectMargin(zsdMargin);
	}
}
