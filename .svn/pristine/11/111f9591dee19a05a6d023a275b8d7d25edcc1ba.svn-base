package com.py.zsdApp.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.py.zsdApp.annotation.SystemControllerLog;
import com.py.zsdApp.entity.ZsdIndustry;
import com.py.zsdApp.service.ZsdIndustryService;
import com.py.zsdApp.utils.Msg;

/**
 * 后管行业管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/zsdindustry")
public class ZsdIndustryController extends BaseController{
	@Autowired
	private ZsdIndustryService zsdIndustryService;
	
	
	/**
	 * 跳转用户列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/industryList";
	}
	
	/**
	 * 获取行业列表数据
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description="查询行业列表")
	@RequestMapping(value = "toListData")
	@ResponseBody
	public Map<String,Object> toListData(HttpServletRequest request) {
		//返回map
		Map<String,Object> resultMap = Maps.newHashMap();
		//获取分页和排序条件
		LayerPage(request);
		//排序插件
		PageHelper.orderBy("id ASC");
		//分页插件
		Page<?> page = PageHelper.startPage(pageNum, pageSize);
		//调用service
		List<ZsdIndustry> list=zsdIndustryService.selectAll();
		//返回layui数据
		resultMap.put("code", 0);
		resultMap.put("msg", "查询成功");
		resultMap.put("count", page.getTotal());
		resultMap.put("data", list);
		return resultMap;
	}
	/**
	 * 跳转form
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toForm")
	public String getIndustryList(HttpServletRequest request,Model model) {
		int id = 0;
		try {
			id= Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		if(id > 0) {
			ZsdIndustry industry = zsdIndustryService.selectByPrimaryKey(id);
			model.addAttribute("obj", industry);
		}
		return "jsp/industryForm";
	}

	/**
	 * 后管新增行业
	 */
	@SystemControllerLog(description="新增行业")
	@RequestMapping(value = "insert")
	@ResponseBody
	public Map<String,Object> insert(HttpServletRequest request){
		//返回map
		Map<String,Object> resultMap = Maps.newHashMap();
		String name=request.getParameter("name");
		String remark=request.getParameter("remark");
		ZsdIndustry industry=new ZsdIndustry();
		industry.setName(name);
		industry.setRemark(remark);
		ZsdIndustry zsdIndustry=zsdIndustryService.selectByExample(industry);
		if(zsdIndustry !=null) {
			resultMap.put("type", "add");
			resultMap.put("code", "fail");
			return resultMap;
		}
		industry.setCreateTime(new Date());
		zsdIndustryService.insertSelective(industry);
		resultMap.put("type", "add");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 修改行业  
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@SystemControllerLog(description="修改行业")
	@RequestMapping(value="update")
	@ResponseBody	
	public Map<String, Object> update(HttpServletRequest request,ZsdIndustry industry) {
		//返回map
		Map<String,Object> resultMap = Maps.newHashMap();
		industry.setCreateTime(new Date());
		zsdIndustryService.updateByPrimaryKeySelective(industry);
		resultMap.put("type", "update");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 删除行业
	 * @param request
	 * @param model
	 * @return
	 */
	@SystemControllerLog(description="删除行业")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,Model model) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		zsdIndustryService.deleteByPrimaryKey(id);
		resultMap.put("type", "delete");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 批量删除行业 
	 * @param request
	 * @param model
	 * @return
	 */
	@SystemControllerLog(description="批量删除行业")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Map<String, Object> batchDelete(HttpServletRequest request,Model model,@RequestParam("ids[]") int[] ids) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		for (int id : ids) {
			zsdIndustryService.deleteByPrimaryKey(id);
		}
		resultMap.put("type", "delete");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 根据ID查询行业详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@SystemControllerLog(description="查询行业详情")
	@RequestMapping("toDetails")
	public String toDetails(HttpServletRequest request, Model model) {
		//行业id
		int id=Integer.parseInt(request.getParameter("id"));
		ZsdIndustry industry=zsdIndustryService.selectByPrimaryKey(id);
		model.addAttribute("obj",industry);
		return "jsp/industryDetails";
	}
	
	/**
	 * 手机端获取所有行业
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Msg list() {
		try {
			List<ZsdIndustry> list=zsdIndustryService.selectAll();
			return Msg.success(list);
		} catch (Exception e) {
			return Msg.fail().add("mag", "处理失败");
		}
	}
	
}
