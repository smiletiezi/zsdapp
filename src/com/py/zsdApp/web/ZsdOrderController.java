package com.py.zsdApp.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.google.common.collect.Maps;
import com.py.zsdApp.annotation.SystemControllerLog;
import com.py.zsdApp.entity.ZsdOrder;
import com.py.zsdApp.entity.ZsdOrderType;
import com.py.zsdApp.service.ZsdOrderService;
import com.py.zsdApp.service.ZsdOrderTypeService;
import com.py.zsdApp.utils.Msg;
import com.py.zsdApp.utils.Utils;

@Controller
@RequestMapping(value = "/order")
public class ZsdOrderController extends BaseController{

	@Autowired
	private ZsdOrderService zsdOrderService;
	@Autowired
	private ZsdOrderTypeService zsdOrderTypeService;
	//发布订单
	@RequestMapping(value = "/releaseOrder")
	@ResponseBody
	public Msg releaseOrder(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="jing",required=false)String jing,//经度
			@RequestParam(value="wei",required=false)String wei,//纬度
			@RequestParam(value="address",required=false)String address,//地址
			@RequestParam(value="deposit",required=false)String deposit,//押金
			@RequestParam(value="img",required=false)String img,//订单图片
			@RequestParam(value="ordertypeid",required=false)String ordertypeid,//订单类型id
			@RequestParam(value="ordertext",required=false)String ordertext,//正文
			@RequestParam(value="price",required=false)String price,//金额
			@RequestParam(value="pricetype",required=false)String pricetype,//交易类型
			@RequestParam(value="phone",required=false)String phone,//电话
			@RequestParam(value="title",required=false)String title) {//订单标题
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		if(!StringUtils.hasText(jing)) {
			return Msg.fail().add("mag", "坐标获取不到");
		}
		if(!StringUtils.hasText(wei)) {
			return Msg.fail().add("mag", "坐标获取不到");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setAddress(address);
		zsdOrder.setCreateTime(new Date());
		zsdOrder.setDeposit(Double.valueOf(deposit));
		zsdOrder.setImg(img);
		zsdOrder.setOrderLat(wei);
		zsdOrder.setOrderLong(jing);
		zsdOrder.setOrderStatus("1");//状态  1待接单  2验资中  3待签收 4已成交
		zsdOrder.setOrdertypeid(Integer.valueOf(ordertypeid));
		zsdOrder.setOrderText(ordertext);
		zsdOrder.setPhone(phone);
		zsdOrder.setPrice(Double.valueOf(price));
		zsdOrder.setPriceType(pricetype);
		zsdOrder.setTitle(title);
		zsdOrder.setUserId(Integer.valueOf(userid));
		zsdOrderService.insertSelective(zsdOrder);
		return Msg.success();
	}
	//待接单的订单
	@RequestMapping(value = "/getAccountEntryOrder")
	@ResponseBody
	public Msg getAccountEntryOrder(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setUserId(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("1");//待接单
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有发布订单");
		}
		return Msg.success(selectOrder);
	}
	//订单撤销
	@RequestMapping(value = "/OrderRevoke")
	@ResponseBody
	public Msg OrderRevoke(@RequestParam(value="orderid",required=false)String orderid) {
		if(!StringUtils.hasText(orderid)) {
			return Msg.fail().add("mag", "订单id不存在");
		}
		ZsdOrder selectByPrimaryKey = zsdOrderService.selectByPrimaryKey(Integer.valueOf(orderid));
		if(selectByPrimaryKey == null) {
			return Msg.fail().add("mag", "订单不存在");
		}
		zsdOrderService.deleteByPrimaryKey(Integer.valueOf(orderid));
		return Msg.success();
	}
	//获取订单详情
	@RequestMapping(value = "/getOrderDetails")
	@ResponseBody
	public Msg getOrderDetails(@RequestParam(value="orderid",required=false)String orderid){
		if(!StringUtils.hasText(orderid)) {
			return Msg.fail().add("mag", "订单id不存在");
		}
		ZsdOrder selectByPrimaryKey = zsdOrderService.selectByPrimaryKey(Integer.valueOf(orderid));
		if(selectByPrimaryKey == null) {
			return Msg.fail().add("mag", "订单不存在");
		}
		return Msg.success(selectByPrimaryKey); 
	}
	//接单
	
	//我接收的订单
	@RequestMapping(value = "/getReceipt")
	@ResponseBody
	public Msg getReceipt(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setBeiyong2(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("2");//已接单  验资中
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有接单请前去首页选择适合您的订单");
		}
		return Msg.success(selectOrder);
	}
	//我发布的订单验资中
	@RequestMapping(value = "/getreleasedReceipt")
	@ResponseBody
	public Msg getreleasedReceipt(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setUserId(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("2");//已接单  验资中
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有被接单订单");
		}
		return Msg.success(selectOrder);
	}
	//待签收
	@RequestMapping(value = "/getWaitingreceipt")
	@ResponseBody
	public Msg getWaitingreceipt(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setUserId(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("3");//已接单  验资中
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有待签收订单");
		}
		return Msg.success(selectOrder);
	}
	//接单人签收
	@RequestMapping(value = "/getSignfor")
	@ResponseBody
	public Msg getSignfor(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setBeiyong2(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("3");//已接单  验资中
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有要签收的订单");
		}
		return Msg.success(selectOrder);
	}
	//发布的已完成
	@RequestMapping(value = "/getfaOrdercomplete")
	@ResponseBody
	public Msg getfaOrdercomplete(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setUserId(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("3");//已接单  验资中
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有完成的订单");
		}
		return Msg.success(selectOrder);
	}
	//接单的已完成的
	@RequestMapping(value = "/getjieOrdercomplete")
	@ResponseBody
	public Msg getjieOrdercomplete(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setBeiyong2(Integer.valueOf(userid));
		zsdOrder.setOrderStatus("3");//已接单  验资中
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有完成的订单");
		}
		return Msg.success(selectOrder);
	}
	//退货售后
	
	//查看电话
	@RequestMapping(value = "/getPhone")
	@ResponseBody
	public Msg getPhone(@RequestParam(value="userid",required=false)String userid){
		
		return Msg.success();
	}
	//验资中的订单
	
	
	
	//获取我的共享
	@RequestMapping(value = "/getOrder")
	@ResponseBody
	public Msg getOrder(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="ordertypeid",required=false)String ordertypeid){
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不存在");
		}
		if(!StringUtils.hasText(ordertypeid)) {
			return Msg.fail().add("mag", "订单类型不存在");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		zsdOrder.setUserId(Integer.valueOf(userid));
		zsdOrder.setOrdertypeid(Integer.valueOf(ordertypeid));
		List<ZsdOrder> selectOrder = zsdOrderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder==null) {
			return Msg.fail().add("mag", "您还没有共享可以前去发布");
		}
		return Msg.success(selectOrder); 
	}
	//接受的订单
	
	//用户接单设置
	@RequestMapping(value = "/UserReceipt")
	@ResponseBody
	public Msg UserReceipt(@RequestParam(value="userid",required=false)String userid){
		
		return Msg.success();
	}
	
	//首页附近订单
	@RequestMapping(value = "/getNearbyOrder")
	@ResponseBody
	public Msg getNearbyOrder(@RequestParam(value="lon",required=false)String lon,
			@RequestParam(value="lat",required=false)String lat) {
		if(StringUtil.isEmpty(lon)||StringUtil.isEmpty(lat)) {
			return Msg.fail().add("msg", "请传入当前位置经纬度");
		}
		Map<String,Object> map=Maps.newHashMap();
		map.put("lon", lon);
		map.put("lat", lat);
		List<ZsdOrder> list=zsdOrderService.selectByLog(map);
		return Msg.success(list);
	}
	
	/*
	 *  首页 附近共享 （发布类型为：广告位 广告器材转让 安装工具 装饰材料）
	 */
	@RequestMapping(value = "/getNearbyShare")
	@ResponseBody
	public Msg getNearbyShare(@RequestParam(value="lon",required=false)String lon,
			@RequestParam(value="lat",required=false)String lat,
			@RequestParam(value="ordertypeid",required=false)Integer ordertypeid) {
		if(StringUtil.isEmpty(lon)||StringUtil.isEmpty(lat)) {
			return Msg.fail().add("msg", "请传入当前位置经纬度");
		}
		Map<String,Object> map=Maps.newHashMap();
		map.put("lon", lon);
		map.put("lat", lat);
		List<ZsdOrder> list=zsdOrderService.selectByLog(map);
		int[] args= {2,3,4,6};
		List<ZsdOrder> orders=new ArrayList<ZsdOrder>();
		if(ordertypeid==null) {
			if(list.size()==0) {
				return Msg.fail().add("msg", "暂未结果");
			}else {
				for(int i=0;i<list.size();i++) {
					for(int j=0;j<args.length;j++) {
						if(args[j]==list.get(i).getOrdertypeid()) {
							orders.add(list.get(i));
						}
					}
				}
			}
			return Msg.success(orders);
		}else {
			if(list.size()==0) {
				return Msg.fail().add("msg", "暂未结果");   
			}else {
				for(int i=0;i<list.size();i++) {
					if(list.get(i).getOrdertypeid()==ordertypeid) {
						orders.add(list.get(i));
					}
				}
			}
			return Msg.success(orders);
		}
	}
	/****************************************************后台订单管理******************************************/
	
	/**
	 * 跳转订单列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/zsdOrderList";
	}	
	
	/**
	 * 获取新闻列表数据
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description="查询新闻列表")
	@RequestMapping(value = "toListData")
	@ResponseBody
	public Map<String,Object> toListData(HttpServletRequest request) {
		ZsdOrder order=new ZsdOrder();
		String title=request.getParameter("title");
		try {
			Integer orderTypeId=Integer.parseInt(request.getParameter("orderTypeId"));
			if(StringUtil.isNotEmpty(orderTypeId.toString())) {
				order.setOrdertypeid(orderTypeId);
			}
		}catch(Exception e){};
		
		
		if(StringUtil.isNotEmpty(title)) {
			order.setTitle(title);
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
		
		List<ZsdOrder> list =zsdOrderService.selectOrder(order);
		//返回layui数据
		resultMap.put("code", 0);
		resultMap.put("msg", "查询成功");
		resultMap.put("count", page.getTotal());
		resultMap.put("data", list);
		return resultMap;
	}
	
	/**
	 * 根据ID查询订单详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@SystemControllerLog(description="查询订单详情")
	@RequestMapping("toDetails")
	public String toDetails(HttpServletRequest request, Model model) {
		String path=Utils.getProperties("attachment_path");
		Integer id=Integer.parseInt(request.getParameter("id"));
		ZsdOrder order =zsdOrderService.selectByPrimaryKey(id);
		List<ZsdOrderType> types=zsdOrderTypeService.selectAll();
		model.addAttribute("obj",order);
		model.addAttribute("path",path);
		model.addAttribute("types",types);
		return "jsp/zsdOrderDetails";
	}
	
	
}
