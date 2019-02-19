package com.py.zsdApp.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.google.common.collect.Maps;
import com.py.zsdApp.annotation.SystemControllerLog;
import com.py.zsdApp.entity.ZsdInsurance;
import com.py.zsdApp.entity.ZsdSafe;
import com.py.zsdApp.service.ZsdInsuranceService;
import com.py.zsdApp.service.ZsdSafeService;
import com.py.zsdApp.utils.Msg;
import com.py.zsdApp.utils.Utils;

@Controller
@RequestMapping("/safe")
public class ZsdSafeController extends BaseController{
@Autowired
private ZsdSafeService zsdSafeService;
@Autowired
private ZsdInsuranceService zsdInsuranceService;

/*
 * 手机端上传照片后（upload/uploadFile） 产生保单  提交购买后 信息不可更改和删除 有问题联系平台 提交后 按钮改为购买中 不可点击
 */
@ResponseBody
@RequestMapping(value="/add")
public Msg add(@RequestParam(value="userid",required=false)Integer userid,
		@RequestParam(value="imgOne",required=false)String imgOne,
		@RequestParam(value="imgTwo",required=false)String imgTwo,
		@RequestParam(value="address",required=false)String address,
		@RequestParam(value="phone",required=false)String phone
		) {	
	ZsdSafe zsdSafe=new ZsdSafe();
	Msg msg=Msg.success();
	try {
		String userId=userid.toString();
		if(StringUtil.isEmpty(userId)) {
			return Msg.fail().add("msg", "用户不能为空");
		}else {
			zsdSafe.setUserid(userid);
			List<ZsdSafe> safe=zsdSafeService.selectByExample(zsdSafe);
			System.out.println(safe.get(0).getType());
			if(safe.size()>0 && safe.get(0).getType().equals("0")){
				msg.add("safe", safe);
				msg.add("msg", "已上传过,去付款");
				return msg;
			}else if(safe.size()>0 && safe.get(0).getType().equals("1")) {
				msg.add("safe", safe);
				msg.add("msg", "已付款。待处理");
				return msg;
			}else if(safe.size()>0 && safe.get(0).getType().equals("2")) {
				msg.add("safe", safe);
				msg.add("msg", "已处理 收到后 请点击确认");
				return msg;
			}else if(safe.size()>0 && safe.get(0).getType().equals("3")) {
				msg.add("safe", safe);
				msg.add("msg", "您已有保单，请去上传保单");
				return msg;
			}
		}
	}catch(Exception e) {}
	if(StringUtil.isNotEmpty(imgOne)) {
		zsdSafe.setImgOne(imgOne);
	}
	if(StringUtil.isNotEmpty(imgTwo)) {
		zsdSafe.setImgTwo(imgTwo);
	}
	if(StringUtil.isNotEmpty(address)) {
		zsdSafe.setAddress(address);
	}
	if(StringUtil.isNotEmpty(phone)) {
		zsdSafe.setPhone(phone);
	}
	zsdSafe.setType("0");
	try {
		zsdSafeService.insertSelective(zsdSafe);
	}catch(Exception e) {
		return Msg.fail().add("msg", "处理失败");
	}
	return Msg.success();
}

/*
 * 手机端获取保险公司信息 保单金额 详情
 */
@ResponseBody
@RequestMapping(value="/insurance")
public Msg insurance() {
	Msg msg=Msg.success();
	ZsdInsurance zsdInsurance=new ZsdInsurance();
	List<ZsdInsurance> insurances=zsdInsuranceService.selectByExample(zsdInsurance);
	msg.add("money", insurances.get(0).getMoney());//保险金额
	msg.add("detail", insurances.get(0).getDetail());//保险详情
	msg.add("remark",insurances.get(0).getRemark() );//温馨提示
	return msg;
}

/*
 * 手机端确认收到保单
 */
@ResponseBody
@RequestMapping(value="/confirm")
public  Msg confirm(@RequestParam(value="userid",required=false)Integer userid) {
	
	try {
		String userId=userid.toString();
		if(StringUtil.isNotEmpty(userId)) {
			ZsdSafe safe=zsdSafeService.selectByPrimaryKey(userid);
			safe.setType("3");
			zsdSafeService.updateByPrimaryKeySelective(safe);
			return Msg.success();
		}else {
			return Msg.fail().add("msg", "用户不能为空");
		}
	}catch(Exception e) {
		return Msg.fail().add("msg", "处理失败");
	}
	
}

/*
 * 手机端上传保单（upload/uploadFile 返回的字符串） 
 */
@ResponseBody
@RequestMapping(value="/upload")
public Msg upload(@RequestParam(value="userid",required=false)Integer userid,
		@RequestParam(value="warranty",required=false)String warranty) {
	ZsdSafe safe=new ZsdSafe();
	if(userid!=null) {
		safe.setUserid(userid);
	}
	List<ZsdSafe> safes=zsdSafeService.selectByExample(safe);
	safes.get(0).setWarranty(warranty);
	zsdSafeService.updateByPrimaryKeySelective(safes.get(0));
	return Msg.success();
}

/*********************************************************购买意外险*****************************************/

/**
 * 跳转购买意外险列表
 * @param request
 * @param model
 * @return
 */
@RequestMapping(value = "toList")
public String toList(HttpServletRequest request,Model model) {
	return "jsp/zsdSafeList";
}	

/**
 * 获取投保订单列表数据
 * @param request
 * @return
 */
@SystemControllerLog(description="查询投保订单列表")
@RequestMapping(value = "toListData")
@ResponseBody
public Map<String,Object> toListData(HttpServletRequest request) {
	String phone=request.getParameter("phone");
	ZsdSafe safe=new ZsdSafe();
	if(StringUtil.isNotEmpty(phone)) {
		safe.setPhone(phone);
	}
	//返回map
			Map<String,Object> resultMap = Maps.newHashMap();
			//获取分页和排序条件
			LayerPage(request);
			//排序插件
			PageHelper.orderBy("id ASC");
			//分页插件
			Page<?> page = PageHelper.startPage(pageNum, pageSize);
	//调用service
	
	List<ZsdSafe> list =zsdSafeService.selectByExample(safe);
	//返回layui数据
	resultMap.put("code", 0);
	resultMap.put("msg", "查询成功");
	resultMap.put("count", page.getTotal());
	resultMap.put("data", list);
	return resultMap;
}

/**
 * 根据ID查询保单详情
 * @param request
 * @param model
 * @param id
 * @return
 */
@SystemControllerLog(description="查询保单详情")
@RequestMapping("toDetails")
public String toDetails(HttpServletRequest request, Model model) {
	String path=Utils.getProperties("attachment_path");
	Integer id=Integer.parseInt(request.getParameter("id"));
	ZsdSafe safe =zsdSafeService.selectByPrimaryKey(id);
	model.addAttribute("obj",safe);
	model.addAttribute("path",path);
	return "jsp/zsdSafeDetails";
}






/*
 * 后台管理 显示出已付款的保单 购买后 点击已购买 后台修改保单状态
 */
@ResponseBody
@RequestMapping(value="/processed")
public Map<String,Object> processed(HttpServletRequest request){
	Map<String,Object> resultMap = Maps.newHashMap();
	Integer id=0;
	try {
		id=Integer.parseInt(request.getParameter("id"));
	}catch(Exception e) {}
	if(id>0) {
		ZsdSafe safe=zsdSafeService.selectByPrimaryKey(id);
		safe.setType("2");
		zsdSafeService.updateByPrimaryKeySelective(safe);
	}
	resultMap.put("code", "0");
	resultMap.put("msg", "success");
	return resultMap;
	
}

/**
 * 页面跳转 邮寄跳转form
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
		ZsdSafe safe = zsdSafeService.selectByPrimaryKey(id);
		model.addAttribute("obj", safe);
	}
	return "jsp/zsdSafeForm";
}

/**
 * 保单邮寄 
 * @param request
 * @param commodityClassification
 * @return
 */
@SystemControllerLog(description="保单邮寄")
@RequestMapping(value="set")
@ResponseBody
public Map<String, Object> set(HttpServletRequest request,@ModelAttribute("obj") ZsdSafe safe) {
	//返回map
	Map<String, Object> resultMap = Maps.newHashMap();
	safe.setType("2");
	zsdSafeService.updateByPrimaryKeySelective(safe);
	resultMap.put("type", "update");
	resultMap.put("code", "success");
	return resultMap;
}

/**
 * 删除保单
 * @param request
 * @param commodityClassification
 * @return
 */
@SystemControllerLog(description="删除保单")
@RequestMapping(value="redelete")
@ResponseBody
public Map<String, Object> redelete(HttpServletRequest request) {
	Integer id=Integer.parseInt(request.getParameter("id"));
	//返回map
	Map<String, Object> resultMap = Maps.newHashMap();
	zsdSafeService.deleteByPrimaryKey(id);
	resultMap.put("type", "delete");
	resultMap.put("code", "success");
	return resultMap;
}



}
