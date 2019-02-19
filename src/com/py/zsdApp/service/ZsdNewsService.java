package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdNewsMapper;
import com.py.zsdApp.entity.ZsdNews;

@Service
public class ZsdNewsService {
@Autowired 
private ZsdNewsMapper zsdNewsMapper;

public int deleteByPrimaryKey(Integer id) {
	return zsdNewsMapper.deleteByPrimaryKey(id);
}

public ZsdNews selectByPrimaryKey(Integer id) {
	return  zsdNewsMapper.selectByPrimaryKey(id);
}
public int insertSelective(ZsdNews record) {
	return zsdNewsMapper.insertSelective(record);
}
public int updateByPrimaryKeySelective(ZsdNews record){
	return zsdNewsMapper.updateByPrimaryKeySelective(record);
}
public List<ZsdNews> selectByTitle(ZsdNews record){
	return zsdNewsMapper.selectByTitle(record);
}
}
