package com.py.zsdApp.dao;


import com.py.zsdApp.entity.ZsdGrade;

public interface ZsdGradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdGrade record);

    int insertSelective(ZsdGrade record);

    ZsdGrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdGrade record);

    int updateByPrimaryKey(ZsdGrade record);
    
    ZsdGrade selectByUserid(Integer userid);
}