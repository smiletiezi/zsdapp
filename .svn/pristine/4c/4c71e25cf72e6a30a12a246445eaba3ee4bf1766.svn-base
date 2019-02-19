package com.py.zsdApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdGradeMapper;
import com.py.zsdApp.entity.ZsdGrade;

@Service
public class ZsdGradeService {

	@Autowired
	private ZsdGradeMapper zsdGradeMapper;
	/**
	 * 新增等级
	 */
	public int insertSelective(ZsdGrade zsdGrade) {
		return zsdGradeMapper.insert(zsdGrade);
	}
	/**
	 *等级修改
	 */
	public int updateByPrimaryKeySelective(ZsdGrade zsdGrade) {
		return zsdGradeMapper.updateByPrimaryKeySelective(zsdGrade);
	}
	/**
	 * 根据userid获取等级
	 */
	public ZsdGrade selectByUserid(Integer userid) {
		return zsdGradeMapper.selectByUserid(userid);
	}
}
