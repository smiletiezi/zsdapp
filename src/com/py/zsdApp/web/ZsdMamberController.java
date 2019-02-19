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
import com.py.zsdApp.entity.ZsdMamber;
import com.py.zsdApp.service.ZsdMamberService;
import com.py.zsdApp.utils.Msg;

@Controller
@RequestMapping("/mamber")
public class ZsdMamberController extends BaseController{
@Autowired
private ZsdMamberService zsdMamberService;

/*
 * 手机端 点击交保障金 时返回保证金额
 */
@ResponseBody
@RequestMapping(value="/getBail")
public Msg getBail() {
	Msg msg=Msg.success();
	ZsdMamber mamber=zsdMamberService.selectByPrimaryKey(1);
	msg.add("bail",mamber.getBail());
	return msg;
}
/*
 * 手机端 点击充会员 时返回会员值
 */
@ResponseBody
@RequestMapping(value="/getMamber")
public Msg getMamber() {
	Msg msg=Msg.success();
	ZsdMamber mamber=zsdMamberService.selectByPrimaryKey(1);
	msg.add("mamber", mamber.getMamber());
	return msg;
}
/******************************************************后台管理****************************************************/
/**
 * 跳转会员充值列表
 * @param request
 * @param model
 * @return
 */
@RequestMapping(value = "toList")
public String toList(HttpServletRequest request,Model model) {
	return "jsp/zsdMamberList";
}	

/**
 * 获取会员值表数据
 * @param request
 * @return
 */
@SystemControllerLog(description="查询会员值列表")
@RequestMapping(value = "toListData")
@ResponseBody
public Map<String,Object> toListData(HttpServletRequest request) {
	ZsdMamber mamber=new ZsdMamber();
	//返回map
			Map<String,Object> resultMap = Maps.newHashMap();
			//获取分页和排序条件
			LayerPage(request);
			//排序插件
			PageHelper.orderBy("id ASC");
			//分页插件
			Page<?> page = PageHelper.startPage(pageNum, pageSize);
	//调用service
	
	List<ZsdMamber> list =zsdMamberService.selectByExample(mamber);
	//返回layui数据
	resultMap.put("code", 0);
	resultMap.put("msg", "查询成功");
	resultMap.put("count", page.getTotal());
	resultMap.put("data", list);
	return resultMap;
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
	if(id > 0) {
		ZsdMamber mamber = zsdMamberService.selectByPrimaryKey(id);
		model.addAttribute("obj", mamber);
	}
	return "jsp/zsdMamberForm";
}

/**
 * 修改充值设置 
 * @param request
 * @param commodityClassification
 * @return
 */
@SystemControllerLog(description="修改充值")
@RequestMapping(value="set")
@ResponseBody
public Map<String, Object> set(HttpServletRequest request,@ModelAttribute("obj") ZsdMamber mamber ) {
	//返回map
	Map<String, Object> resultMap = Maps.newHashMap();
	zsdMamberService.updateByPrimaryKeySelective(mamber);
	resultMap.put("type", "update");
	resultMap.put("code", "success");
	return resultMap;
}


}
