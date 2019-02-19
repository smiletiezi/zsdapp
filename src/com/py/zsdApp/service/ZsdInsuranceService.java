package com.py.zsdApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.py.zsdApp.dao.ZsdInsuranceMapper;
import com.py.zsdApp.entity.ZsdInsurance;


@Service
public class ZsdInsuranceService {
@Autowired
private ZsdInsuranceMapper zsdInsuranceMapper;

public int updateByPrimaryKeySelective(ZsdInsurance zsdInsurance) {
	return zsdInsuranceMapper.updateByPrimaryKeySelective(zsdInsurance);
}

public int insertSelective(ZsdInsurance zsdInsurance) {
	return zsdInsuranceMapper.insertSelective(zsdInsurance);
}

public List<ZsdInsurance> selectByExample(ZsdInsurance zsdInsurance){
	return zsdInsuranceMapper.selectByExample(zsdInsurance);
}
public ZsdInsurance selectByPrimaryKey(Integer id) {
	return zsdInsuranceMapper.selectByPrimaryKey(id);
}
}
