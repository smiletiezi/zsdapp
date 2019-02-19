package com.py.zsdApp.web;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.py.zsdApp.annotation.SystemControllerLog;
import com.py.zsdApp.entity.ZsdInsurance;
import com.py.zsdApp.service.ZsdInsuranceService;
/*
 * 保险公司
 */
@Controller
@RequestMapping("/insurance")
public class ZsdInsuranceController extends BaseController{
@Autowired
private ZsdInsuranceService zsdInsuranceService;

/**
 * 跳转保险列表
 * @param request
 * @param model
 * @return
 */
@RequestMapping(value = "toList")
public String toList(HttpServletRequest request,Model model) {
	return "jsp/zsdInsuranceList";
}	

/**
 * 获取保险列表数据
 * @param request
 * @return
 */
@SystemControllerLog(description="查询保险列表")
@RequestMapping(value = "toListData")
@ResponseBody
public Map<String,Object> toListData(HttpServletRequest request) {
	ZsdInsurance zsdInsurance=new ZsdInsurance();
	//返回map
			Map<String,Object> resultMap = Maps.newHashMap();
			//获取分页和排序条件
			LayerPage(request);
			//排序插件
			PageHelper.orderBy("id ASC");
			//分页插件
			Page<?> page = PageHelper.startPage(pageNum, pageSize);
	//调用service
	
	List<ZsdInsurance> list =zsdInsuranceService.selectByExample(zsdInsurance);
	//返回layui数据
	resultMap.put("code", 0);
	resultMap.put("msg", "查询成功");
	resultMap.put("count", page.getTotal());
	resultMap.put("data", list);
	return resultMap;
}

/**
 * 根据ID查询保险详情
 * @param request
 * @param model
 * @param id
 * @return
 */
@SystemControllerLog(description="查询保险详情")
@RequestMapping("toDetails")
public String toDetails(HttpServletRequest request, Model model) {
	Integer id=Integer.parseInt(request.getParameter("id"));
	ZsdInsurance zsdInsurance =zsdInsuranceService.selectByPrimaryKey(id);
	model.addAttribute("obj",zsdInsurance);
	return "jsp/zsdInsuranceDetails";
}

/**
 * 页面跳转 新增或修改跳转form
 * @param request
 * @param model
 * @return
 */
@RequestMapping(value = "toForm")
public String getUserList(HttpServletRequest request,Model model) {
	int id = 0;
	try {
		id= Integer.parseInt(request.getParameter("id"));
	} catch (Exception e) {
		id = 0;
	}
	if(id>0) {
	ZsdInsurance zsdInsurance = zsdInsuranceService.selectByPrimaryKey(id);
		model.addAttribute("obj", zsdInsurance);
	}
	return "jsp/zsdInsuranceForm";
}

/**
 * 修改
 * @param request
 * @param commodityClassification
 * @return
 */
@SystemControllerLog(description="修改")
@RequestMapping(value="set")
@ResponseBody
public Map<String, Object> set(HttpServletRequest request,@ModelAttribute("obj") ZsdInsurance zsdInsurance) {
	//返回map
	Map<String, Object> resultMap = Maps.newHashMap();
	zsdInsuranceService.updateByPrimaryKeySelective(zsdInsurance);
	resultMap.put("type", "update");
	resultMap.put("code", "success");
	return resultMap;
}

}
