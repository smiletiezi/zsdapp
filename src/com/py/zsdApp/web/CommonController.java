package com.py.zsdApp.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliyuncs.exceptions.ClientException;
import com.py.zsdApp.entity.ZsdUser;
import com.py.zsdApp.service.ZsdUserService;
import com.py.zsdApp.utils.Msg;
import com.py.zsdApp.utils.SMSBean;
import com.py.zsdApp.utils.SendMSMUtil;

@Controller
@RequestMapping("/common")
public class CommonController {
	@Autowired
	private ZsdUserService zsdUserService;
	/**
	 * 获取验证码
	 * @param phonenum
	 * @param type
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCode")
	public Msg getCode(@RequestParam(value="userPhone",required=false)String userPhone,
			@RequestParam(value="type",required=false)String type) {
		if(userPhone == null || "".equals(userPhone)) {
			return Msg.fail().add("msg", "电话号码为空");
		}
		ZsdUser user = new ZsdUser();
		user.setUserPhone(userPhone);
		 String smsTpl = null;
		if("register".equals(type)) {
			smsTpl = SendMSMUtil.COMMON_TEMPLATE;
			ZsdUser users = zsdUserService.selectByUser(user);
			if(users !=null) {
				return Msg.fail().add("msg", "该手机已注册");
			}
		}else {
			ZsdUser users = zsdUserService.selectByUser(user);
			if(users==null) {
				return Msg.fail().add("msg", "该手机未注册");
			}
			smsTpl = SendMSMUtil.COMMON_TEMPLATE_UPDATE;
		}try{
	        SMSBean smsBean = SendMSMUtil.sendMSM(userPhone, smsTpl, true, null);
	        if (smsBean == null) {
	        	return Msg.fail().add("msg", "短信发送失败");
	        } else { 
	        	return Msg.success();
	        }
	      }catch (ClientException e){
	        e.printStackTrace();
	        return Msg.fail().add("msg", "短信发送失败");
	      }
	}
	
}
