package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.py.zsdApp.dao.ZsdMamberMapper;
import com.py.zsdApp.entity.ZsdMamber;

@Service
public class ZsdMamberService {
@Autowired
private ZsdMamberMapper zsdMamberMapper;

public int updateByPrimaryKeySelective(ZsdMamber record) {
	return zsdMamberMapper.updateByPrimaryKeySelective(record);
}
public ZsdMamber selectByPrimaryKey(Integer id) {
	return zsdMamberMapper.selectByPrimaryKey(id);
}
public List<ZsdMamber> selectByExample(ZsdMamber record){
	return zsdMamberMapper.selectByExample(record);
}
}
