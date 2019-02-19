package com.py.zsdApp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdUserMapper;
import com.py.zsdApp.entity.ZsdUser;

@Service
public class ZsdUserService {
	@Autowired
	private ZsdUserMapper zsdUserMapper;
	
	/**
	 * 用户新增
	 */
	public int insertSelective(ZsdUser user) {
		return zsdUserMapper.insert(user);
	}
	
	/**
	 * 用户修改
	 */
	public int updateByPrimaryKeySelective(ZsdUser record) {
		return zsdUserMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 用户删除
	 */
	
	public int deleteByPrimaryKey(Integer userId) {
		return zsdUserMapper.deleteByPrimaryKey(userId);
	}
	
	/**
	 * 根据id查询用户
	 */
	
	public ZsdUser selectByPrimaryKey(Integer userId) {
		return zsdUserMapper.selectByPrimaryKey(userId);
	}
	
	/**
	 * 根据用户属性查询
	 */
	public ZsdUser selectByUser(ZsdUser record){
		return zsdUserMapper.selectByUser(record);
	}
	
	/**
	 * 根据用户属性查询
	 */
	public List<ZsdUser> selectByUserIndustry(ZsdUser record){
		return zsdUserMapper.selectByUserIndustry(record);
	}
	
	/**
	 * 根据经纬度查询附近
	 */
	 public List<ZsdUser> selectByLog(Map<String,Object> map){
		 return zsdUserMapper.selectByLog(map);
	 }
}
