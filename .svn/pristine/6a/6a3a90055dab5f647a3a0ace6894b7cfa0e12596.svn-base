package com.py.zsdApp.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.py.zsdApp.entity.ZsdMargin;
import com.py.zsdApp.entity.ZsdUser;
import com.py.zsdApp.service.ZsdMarginService;
import com.py.zsdApp.service.ZsdUserService;
import com.py.zsdApp.utils.Msg;

@Controller
@RequestMapping(value = "/margin")
public class ZsdMarginController {

	@Autowired
	private ZsdMarginService zsdMarginService;
	@Autowired
	private ZsdUserService zsdUserService;
	@RequestMapping(value = "/addMargin")
	@ResponseBody
	public Msg addMargin(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="money",required=false)String money) {
		if(!StringUtils.hasText(userid)) {
			return Msg.fail().add("mag", "用户id不能为空");
		}
		ZsdMargin zsdMargin = new ZsdMargin();
		zsdMargin.setUserId(Integer.valueOf(userid));
		ZsdMargin selectMargin = zsdMarginService.selectMargin(zsdMargin);
		if(selectMargin != null) {
			return Msg.fail().add("mag", "您已经交过保证金了");
		}
		ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(userid));
		Double userBalance = selectByPrimaryKey.getUserBalance();
		Integer valueOf = Integer.valueOf(money);
		if(userBalance<valueOf) {
			return Msg.fail().add("mag", "余额不足请前往个人中心充值");
		}
		ZsdMargin margin = new ZsdMargin();
		margin.setMoney(Integer.valueOf(money));
		margin.setUserId(Integer.valueOf(userid));
		margin.setCreateTime(new Date());
		zsdMarginService.insertSelective(margin);
		return Msg.success();
	}
	@RequestMapping(value = "/getMargin")
	@ResponseBody
	public Msg getMargin(@RequestParam(value="userid",required=false)String userid) {
		
		return Msg.success();
	}
}
