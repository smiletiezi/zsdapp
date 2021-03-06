package com.py.zsdApp.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.py.zsdApp.entity.ZsdOrder;
import com.py.zsdApp.entity.ZsdUser;
import com.py.zsdApp.entity.ZsdOrderType;
import com.py.zsdApp.service.ZsdOrderService;
import com.py.zsdApp.service.ZsdOrderTypeService;
import com.py.zsdApp.service.ZsdUserService;
import com.py.zsdApp.utils.MapHelper;
import com.py.zsdApp.utils.Msg;

@Controller
@RequestMapping(value = "/home")
public class HomePageController {
	@Autowired
	private ZsdUserService zsdUserService;
	@Autowired
	private ZsdOrderTypeService zsdOrderTypeService;
	@Autowired
	private ZsdOrderService orderService;
	
	//获取最近的工人
	@RequestMapping(value = "/getNearbyWorker")
	@ResponseBody
	public Msg getNearbyWorker(@RequestParam(value="typework",required=false)String typework,
			@RequestParam(value="userLat",required=false)String userLat,
			@RequestParam(value="userLong",required=false)String userLong){
		System.out.println(typework);
		if(!StringUtils.hasText(userLat)) {//纬度
			return Msg.fail().add("mag", "用户坐标为空请定位");
		}
		if(!StringUtils.hasText(userLong)) {//经度
			return Msg.fail().add("mag", "用户坐标为空请定位");
		}
		ZsdUser zsdUser = new ZsdUser();
		zsdUser.setUserIndustry(typework);
		Double jing = Double.valueOf(userLong);//当前用户经度
		Double wei = Double.valueOf(userLat);//当前用户纬度
		List<ZsdUser> selectByUser = zsdUserService.selectByUserIndustry(zsdUser);
		if(selectByUser.size() == 0||selectByUser == null) {
			return Msg.fail().add("mag", "该行业还没有工人");
		}
		List<Map<String,String>> list = new ArrayList<>();
		for (ZsdUser zsdUser2 : selectByUser) {
			if(StringUtils.hasText(zsdUser2.getUserAddress())&&StringUtils.hasText(zsdUser2.getUserLat())&&StringUtils.hasText(zsdUser2.getUserLong())) {
				String userLat2 = zsdUser2.getUserLat();//工人纬度
				String userLong2 = zsdUser2.getUserLong();//工人经度
				Double gjing = Double.valueOf(userLong2);//当前工人经度
				Double gwei = Double.valueOf(userLat2);//当前工人纬度
				Double distance = MapHelper.getDistance(wei,jing, gwei, gjing);//距离
				Map<String, String> map = new HashMap<String,String>();
				map.put("id",zsdUser2.getUserId().toString());//用户
				map.put("username",zsdUser2.getUserName());//名称
				map.put("juli", distance.toString());//距离
				map.put("image", zsdUser2.getUserImg());//头像
				map.put("industry", zsdUser2.getUserIndustry());//行业
				map.put("speciality", zsdUser2.getUserSpare2());//特长
				list.add(map);
			}
		}
		Collections.sort(list, new Comparator<Map<String,String>>() {
		      public int compare(Map<String, String> o1, Map<String, String> o2) {
		        String name1 = (String)o1.get("juli");
		        String name2 = (String)o2.get("juli");
		        return name1.compareTo(name2);
		      }
		});
		return Msg.success(list);
	}
	//获取订单类型
	@RequestMapping(value = "/getOrderType")
	@ResponseBody
	public Msg getOrderType() {
		List<ZsdOrderType> selectAll = zsdOrderTypeService.selectAll();
		if(selectAll.size()==0||selectAll==null) {
			return Msg.fail().add("mag", "订单类型为空");
		}
		return Msg.success(selectAll);
	}
	//获取附近最近订单
	@RequestMapping(value = "/getNearbyOrder")
	@ResponseBody
	public Msg getNearbyOrder(@RequestParam(value="ordertype",required=false)String ordertype,
			@RequestParam(value="userLat",required=false)String userLat,
			@RequestParam(value="userLong",required=false)String userLong) {
		if(!StringUtils.hasText(userLat)) {//纬度
			return Msg.fail().add("mag", "用户坐标为空请定位");
		}
		if(!StringUtils.hasText(userLong)) {//经度
			return Msg.fail().add("mag", "用户坐标为空请定位");
		}
		ZsdOrder zsdOrder = new ZsdOrder();
		if(StringUtils.hasText(ordertype)) {
			zsdOrder.setOrdertypeid(Integer.valueOf(ordertype));
		}
		zsdOrder.setOrderStatus("1");
		Double jing = Double.valueOf(userLong);//当前用户经度
		Double wei = Double.valueOf(userLat);//当前用户纬度
		List<ZsdOrder> selectOrder = orderService.selectOrder(zsdOrder);
		if(selectOrder.size() == 0||selectOrder == null) {
			return Msg.fail().add("mag", "还没有订单");
		}
		List<Map<String,String>> list = new ArrayList<>();
		for (ZsdOrder zsdUser2 : selectOrder) {
			if(StringUtils.hasText(zsdUser2.getAddress())&&StringUtils.hasText(zsdUser2.getOrderLat())&&StringUtils.hasText(zsdUser2.getOrderLong())) {
				String userLat2 = zsdUser2.getOrderLat();//订单纬度
				String userLong2 = zsdUser2.getOrderLong();//订单经度
				Double gjing = Double.valueOf(userLong2);//当前搜索人经度
				Double gwei = Double.valueOf(userLat2);//当前搜索人纬度
				Double distance = MapHelper.getDistance(wei,jing, gwei, gjing);//距离
				Map<String, String> map = new HashMap<String,String>();
				map.put("id",zsdUser2.getId().toString());//订单id
				map.put("title",zsdUser2.getTitle());
				ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(zsdUser2.getUserId()));
				if(selectByPrimaryKey == null) {
					return Msg.fail().add("mag", "订单用户不存在");
				}
				ZsdOrderType selectByPrimaryKey2 = zsdOrderTypeService.selectByPrimaryKey(Integer.valueOf(zsdUser2.getOrdertypeid()));
				if(selectByPrimaryKey2 == null) {
					return Msg.fail().add("mag", "订单类型不存在");
				}
				map.put("type",selectByPrimaryKey2.getName());//订单类型
				map.put("username",selectByPrimaryKey.getUserName());//名称
				map.put("juli", distance.toString());//距离
				map.put("image", selectByPrimaryKey.getUserImg());//头像
				map.put("userid", selectByPrimaryKey.getUserId().toString());//发布人id
				list.add(map);
			}
		}
		Collections.sort(list, new Comparator<Map<String,String>>() {
		      public int compare(Map<String, String> o1, Map<String, String> o2) {
		        String name1 = (String)o1.get("juli");
		        String name2 = (String)o2.get("juli");
		        return name1.compareTo(name2);
		      }
		});
		return Msg.success(list);
	}
	//查看订单
	@RequestMapping(value = "/getNearbyOrderDetails")
	@ResponseBody
	public Msg getNearbyOrderDetails(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="orderid",required=false)String orderid) {
		
		return Msg.success();
	}
	//接单
	@RequestMapping(value = "/UserReceipt")
	@ResponseBody
	public Msg UserReceipt(@RequestParam(value="userid",required=false)String userid) {
		
		return Msg.success();
	}
	//获取附近的企业
	
	//获取附近的共享
	
}
