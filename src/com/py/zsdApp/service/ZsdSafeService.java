package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdSafeMapper;
import com.py.zsdApp.entity.ZsdSafe;

@Service
public class ZsdSafeService {
@Autowired
private ZsdSafeMapper zsdSafeMapper;

public int insertSelective(ZsdSafe zsdSafe) {
	return zsdSafeMapper.insertSelective(zsdSafe);
}
public ZsdSafe selectByPrimaryKey(Integer id) {
	return zsdSafeMapper.selectByPrimaryKey(id);
}
public int updateByPrimaryKeySelective(ZsdSafe zsdSafe) {
	return zsdSafeMapper.updateByPrimaryKeySelective(zsdSafe);
}
public List<ZsdSafe> selectByExample(ZsdSafe zsdSafe){
	return zsdSafeMapper.selectByExample(zsdSafe);
}
public int deleteByPrimaryKey(Integer id) {
	return zsdSafeMapper.deleteByPrimaryKey(id);
}
}
