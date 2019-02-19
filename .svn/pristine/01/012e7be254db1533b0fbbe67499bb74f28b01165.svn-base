package com.py.zsdApp.dao;

import java.util.List;

import com.py.zsdApp.entity.ZsdIndustry;

public interface ZsdIndustryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsdIndustry record);

    int insertSelective(ZsdIndustry record);

    ZsdIndustry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsdIndustry record);

    int updateByPrimaryKey(ZsdIndustry record);
    
  //手机端获取行业列表
    List<ZsdIndustry> selectByParentId(Integer parentId);
    //后管获取行业列表
    List<ZsdIndustry> selectAll();
    //后管根据属性查询
    ZsdIndustry selectByExample(ZsdIndustry record);

}