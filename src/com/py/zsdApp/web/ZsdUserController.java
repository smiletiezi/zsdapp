package com.py.zsdApp.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.StringUtil;
import com.py.zsdApp.annotation.SystemControllerLog;
import com.py.zsdApp.entity.ZsdAboutUs;
import com.py.zsdApp.entity.ZsdAddress;
import com.py.zsdApp.entity.ZsdGrade;
import com.py.zsdApp.entity.ZsdIndustry;
import com.py.zsdApp.entity.ZsdIntegral;
import com.py.zsdApp.entity.ZsdIntegralDetails;
import com.py.zsdApp.entity.ZsdMaillist;
import com.py.zsdApp.entity.ZsdSignin;
import com.py.zsdApp.entity.ZsdSigninrecord;
import com.py.zsdApp.entity.ZsdUser;
import com.py.zsdApp.service.ZsdAboutUsService;
import com.py.zsdApp.service.ZsdAddressService;
import com.py.zsdApp.service.ZsdGradeService;
import com.py.zsdApp.service.ZsdIndustryService;
import com.py.zsdApp.service.ZsdIntegralDetailsService;
import com.py.zsdApp.service.ZsdIntegralService;
import com.py.zsdApp.service.ZsdMaillistService;
import com.py.zsdApp.service.ZsdSigninService;
import com.py.zsdApp.service.ZsdSigninrecordService;
import com.py.zsdApp.service.ZsdUserService;
import com.py.zsdApp.utils.CommonUtil;
import com.py.zsdApp.utils.FileUtil;
import com.py.zsdApp.utils.JpushUtil;
import com.py.zsdApp.utils.MD5;
import com.py.zsdApp.utils.Msg;
import com.py.zsdApp.utils.SMSBean;
import com.py.zsdApp.utils.UUIDUtils;
@Controller
@RequestMapping(value = "/zsduser")
public class ZsdUserController {
	@Value("C:/uplocadImage/imageFile")
	private String imageFile;
	@Autowired
	private ZsdUserService zsdUserService;
	@Autowired
	private ZsdAddressService zsdAddressService;
	@Autowired
	private ZsdGradeService zsdGradeService;
	@Autowired
	private ZsdMaillistService zsdMaillistService;
	@Autowired
	private ZsdAboutUsService zsdAboutUsService;
	@Autowired
	private ZsdSigninService zsdSigninService;
	@Autowired
	private ZsdSigninrecordService zsdSigninrecordService;
	@Autowired
	private ZsdIndustryService zsdIndustryService;
	@Autowired
	private ZsdIntegralDetailsService zsdIntegralDetailsService;
	@Autowired
	private ZsdIntegralService zsdIntegralService;
	
	/*
	 * 手机端注册
	 */
	@SystemControllerLog(description="手機端注冊")
	@RequestMapping(value = "/register")
	@ResponseBody
	public Msg register(@RequestParam(value="userPhone",required=false)String userPhone,
			@RequestParam(value="userPassword",required=false)String userPassword,
			@RequestParam(value="code",required=false)String code,
			@RequestParam(value="type",required=false)Integer type){
		ZsdUser user=new ZsdUser();
		if(userPhone !=null && userPhone !="") {
			if(userPhone.length()!=11) {
				return Msg.fail().add("mag", "手机号应为11位数");
			}else {
				String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
				Pattern p = Pattern.compile(regex);
	            Matcher m = p.matcher(userPhone);
	            boolean isMatch = m.matches();
	            if(isMatch){
	                user.setUserPhone(userPhone);
	            } else {
					return Msg.fail().add("mag", "手机号格式错误");
	            }
			}
		}else {
			return Msg.fail().add("mag", "手机号不能为空");
		}
		ZsdUser users=zsdUserService.selectByUser(user);
		if(users!=null){
			return Msg.fail().add("mag", "该手机号已注册");
		}
		if(code == null) {
			return Msg.fail().add("mag", "请输入验证码");
			}
		Msg msg = CommonUtil.verifyMobileCode(userPhone, code );
		if(msg.getCode() != 100) {
			return msg;
		}
		if(userPassword !=null&&userPassword !="") {
			String password =MD5.MD5(userPassword);
			user.setUserPassword(password);
		}else {
			return Msg.fail().add("mag", "密码为空");
		}
		try {
			user.setUserCreateTime(new Date());
			//注册类型
			user.setUserSpare(type);
			zsdUserService.insertSelective(user);
			//等级表
			ZsdGrade zsdGrade = new ZsdGrade();
			zsdGrade.setCreateTime(new Date());
			zsdGrade.setUserGrade("0");
			zsdGrade.setUserId(user.getUserId());
			zsdGradeService.insertSelective(zsdGrade);
			//签到表
			ZsdSignin zsdSignin = new ZsdSignin();
			zsdSignin.setStatus("2");
			zsdSignin.setAccumulateDay("0");
			Date upDay = getUpDay(new Date());
			zsdSignin.setTime(upDay);
			zsdSignin.setUserId(user.getUserId());
			zsdSigninService.insertSelective(zsdSignin);
			//积分表
			ZsdIntegral zsdIntegral = new ZsdIntegral();
			zsdIntegral.setCreateTime(new Date());
			zsdIntegral.setIntegral(0);
			zsdIntegral.setSignscore(0);
			zsdIntegral.setUserId(user.getUserId());
			zsdIntegralService.insertSelective(zsdIntegral);
			return Msg.success();
		} catch (Exception e) {
			return Msg.fail().add("mag", "处理失败");
		}
	}
	/**
	 * 手机端登录
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Msg login(@RequestParam(value="userPhone",required=false)String userPhone,
			@RequestParam(value="userPassword",required=false)String userPassword){
		ZsdUser user =new ZsdUser();
		if(StringUtil.isNotEmpty(userPhone)) {
			user.setUserPhone(userPhone);
		}else {
			return Msg.fail().add("msg", "登录名为空");
		}
		if(StringUtil.isNotEmpty(userPassword)) {
			user.setUserPassword(MD5.MD5(userPassword));
		}else {
			return Msg.fail().add("msg", "密码为空");
		}
		ZsdUser users=zsdUserService.selectByUser(user);
		if(users==null) {
			return Msg.fail().add("msg", "用户名或者密码不正确");
		}
		SMSBean smsBean = (SMSBean) CommonUtil.MSG_MAP.get(users.getUserPhone());
		if(smsBean!=null){
			//推送信息系统检测到您的账号在另外一台手机登录,如非本人操作请修改密码
			if(users!=null) {
				try {
             		JpushUtil.pushToAliasMessage("系统检测到您的账号在另外一台手机登录,如非本人操作请修改密码",users.getUserId().toString());
 				} catch (Exception e) {
 				}	
				CommonUtil.MSG_MAP.remove(users.getUserPhone());
			}	
		}
		SMSBean bean = new SMSBean(users.getUserId(),users.getUserPhone(),UUIDUtils.getUUID(),null);
		CommonUtil.MSG_MAP.put(users.getUserPhone(), bean);
		try {
			users.setUserLoginTime(new Date());
			zsdUserService.updateByPrimaryKeySelective(users);
			Msg msg=Msg.success();
			msg.add("userId",users.getUserId().toString());
			return msg;
		} catch (Exception e) {
			return Msg.fail().add("mag", "处理失败");
		}
		
	}
	
	/*
	 *手机端忘记密码
	 */
	@RequestMapping(value = "/forget")
	@ResponseBody
	public Msg forget(@RequestParam(value="userPhone",required=false)String userPhone,
			@RequestParam(value="userPassword",required=false)String userPassword,
			@RequestParam(value="code",required=false)String code) {
		ZsdUser user=new ZsdUser();
		if(userPhone !=null && userPhone !="") {
			if(userPhone.length()!=11) {
				return Msg.fail().add("mag", "手机号应为11位数");
			}else {
				String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
				Pattern p = Pattern.compile(regex);
	            Matcher m = p.matcher(userPhone);
	            boolean isMatch = m.matches();
	            if(isMatch){
	                user.setUserPhone(userPhone);
	            } else {
					return Msg.fail().add("mag", "手机号格式错误");
	            }
			}
		}else {
			return Msg.fail().add("mag", "手机号不能为空");
		}
		ZsdUser users=zsdUserService.selectByUser(user);
		if(users==null) {
			return Msg.fail().add("mag", "改手机号未注册");
		}
		if(code == null) {
			return Msg.fail().add("mag", "请输入验证码");
			}
		Msg msg = CommonUtil.verifyMobileCode(userPhone, code );
		if(msg.getCode() != 100) {
			return msg;
		}
		if(userPassword==null) {
			return Msg.fail().add("mag", "请输入新密码");
		}
		try {
			users.setUserPassword(MD5.MD5(userPassword));
			zsdUserService.updateByPrimaryKeySelective(users);
			return Msg.success();
		} catch (Exception e) {
			return Msg.fail().add("mag", "处理失败");
		}
		
	}
	
 /*
  * 手机端修改密码
  */
	@RequestMapping(value = "/reset")
	@ResponseBody
	public Msg reset(@RequestParam(value="userId",required=false)Integer userId,
			@RequestParam(value="userPassword",required=false)String userPassword,
			@RequestParam(value="newPassword",required=false)String newPassword,
			@RequestParam(value="rePassword",required=false)String rePassword){
		ZsdUser user=zsdUserService.selectByPrimaryKey(userId);
		if(user==null){
			return Msg.fail().add("mag", "请重新登录");
		}
		if(userPassword!=null){
			if(user.getUserPassword().equals(MD5.MD5(userPassword))==false) {
				return Msg.fail().add("mag", "原始密码不正确");
			}
		}else {
			return Msg.fail().add("mag", "请输入原始密码");
		}
		if(newPassword==null) {
			return Msg.fail().add("mag", "请输入新密码");
		}
		if(rePassword==null) {
			return Msg.fail().add("mag", "请再次输入新密码");
		}
		if(newPassword.equals(rePassword)==false) {
			return Msg.fail().add("mag", "两次输入的密码不一致");
		}
		user.setUserPassword(MD5.MD5(newPassword));
		
		try {
			zsdUserService.updateByPrimaryKeySelective(user);
			return Msg.success();
		} catch (Exception e) {
			return Msg.fail().add("mag", "处理失败");
		}
	}
	/**
	 * 手机端获取余额    //交易余额还没返回
	 */
	@RequestMapping(value = "/getUserBalance")
	@ResponseBody
	public Msg getUserBalance(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id为空");
		}
		ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(userid));//查询单个用户信息
		if(selectByPrimaryKey == null){
			return Msg.fail().add("mag", "用户不存在");
		}
		Double userBalance = selectByPrimaryKey.getUserBalance();
		return Msg.success(userBalance);
	}
//	/**
//	 * 手机端修改交易密码
//	 */
//	@RequestMapping(value = "/updatetranPassword")
//	@ResponseBody
//	public Msg updatetranPassword(@RequestParam(value="userid",required=false)String userid,
//			@RequestParam(value="newPassword",required=false)String newPassword){
//		if(!StringUtils.hasText(userid)){
//			return Msg.fail().add("mag", "用户id为空");
//		}
//		if(!StringUtils.hasText(userid)){
//			return Msg.fail().add("mag", "新密码为空");
//		}
//		ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(userid));//查询单个用户信息
//		if(selectByPrimaryKey == null){
//			return Msg.fail().add("mag", "用户不存在");
//		}
//		return Msg.success();
//	}
	/**
	 * 手机端获取地址
	 */
	@RequestMapping(value = "/getUserAddress")
	@ResponseBody
	public Msg getUserAddress(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不存在");
		}
		List<ZsdAddress> selectByUserid = zsdAddressService.selectByUserid(Integer.valueOf(userid));
		if(selectByUserid.size() == 0||selectByUserid == null) {
			return Msg.fail().add("mag", "您还没有地址");
		}
		return Msg.success(selectByUserid);
	}
	/**
	 * 手机端添加地址
	 */
	@RequestMapping(value = "/addUserAddress")
	@ResponseBody
	public Msg addUserAddress(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="address",required=false)String address){
		System.out.println(address);
		if(!StringUtils.hasText(address)){
			return Msg.fail().add("mag", "地址不存在");
		}
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不存在");
		}
		ZsdAddress zsdAddress = new ZsdAddress();
		zsdAddress.setCreateTime(new Date());
		zsdAddress.setStatus("1");
		zsdAddress.setUserId(Integer.valueOf(userid));
		zsdAddress.setAddress(address);
		zsdAddressService.insertSelective(zsdAddress);
		return Msg.success();
	}
	/**
	 * 手机端修改地址
	 */
	@RequestMapping(value = "/updateUserAddress")
	@ResponseBody
	public Msg updateUserAddress(@RequestParam(value="addressid",required=false)String addressid,
			@RequestParam(value="address",required=false)String address){
		if(!StringUtils.hasText(address)){
			return Msg.fail().add("mag", "地址不存在");
		}
		if(!StringUtils.hasText(addressid)){
			return Msg.fail().add("mag", "地址id不存在");
		}
		ZsdAddress selectByPrimaryKey = zsdAddressService.selectByPrimaryKey(Integer.valueOf(addressid));
		if(selectByPrimaryKey == null) {
			return Msg.fail().add("mag", "用户地址不存在");
		}
		selectByPrimaryKey.setAddress(address);
		zsdAddressService.insertSelective(selectByPrimaryKey);
		return Msg.success();
	}
	/**
	 * 手机端删除地址
	 */
	@RequestMapping(value = "/deleteUserAddress")
	@ResponseBody
	public Msg deleteUserAddress(@RequestParam(value="addressid",required=false)String addressid){
		if(!StringUtils.hasText(addressid)){
			return Msg.fail().add("mag", "地址id不存在");
		}
		zsdAddressService.deleteByPrimaryKey(Integer.valueOf(addressid));
		return Msg.success();
	}
	/**
	 * 手机端上传图片
	 */
	@RequestMapping(value = "/UploadImage")
	@ResponseBody
	public Msg UploadImage(@RequestParam(value = "file",required=false) MultipartFile file){
		System.out.println(file);
	    String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf("."));
	    String randomName = UUID.randomUUID().toString().replaceAll("-", "");
	    String newFileName = randomName + suffix;

	    String filePath = imageFile + "/";
	    try{
	      FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
	    } catch (Exception e) {
	    	return Msg.fail().add("mag",e.getMessage());
	    }
	    return Msg.success(newFileName);
	}
	/**
	 * 手机端获取个人信息
	 */
	@RequestMapping(value = "/userinformation")
	@ResponseBody
	public Msg getUserInformation(@RequestParam(value="userid",required=false)String userid){
		ZsdUser user=new ZsdUser();
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id为空");
		}
		user.setUserId(Integer.valueOf(userid));
		ZsdUser users=zsdUserService.selectByUser(user);
		if (users == null) {
			return Msg.fail().add("mag", "用户不存在");
		}
		return Msg.success(users);
	}
	/**
	 * 手机端修改个人信息
	 * userName  用户名
	 * userImg  头像
	 * userBirthday 生日
	 * userSex  性别
	 * userIndustry 行业/专业
	 * userBrand  产品/品牌
	 */
	@RequestMapping(value = "/updateUserInformation")
	@ResponseBody
	public Msg updateUserInformation(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="userName",required=false)String userName,
			@RequestParam(value="userImg",required=false)String userImg,
			@RequestParam(value="userBirthday",required=false)String userBirthday,
			@RequestParam(value="userSex",required=false)String userSex,
			@RequestParam(value="userIndustry",required=false)String userIndustry,
			@RequestParam(value="userBrand",required=false)String userBrand,
			@RequestParam(value="address",required=false)String address,
			@RequestParam(value="jing",required=false)String jing,
			@RequestParam(value="wei",required=false)String wei,
			@RequestParam(value="speciality",required=false)String speciality){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户不能为空");
		}
//		if(!StringUtils.hasText(userName)){
//			return Msg.fail().add("mag", "用户名称不能为空");
//		}
//		if(!StringUtils.hasText(userImg)){
//			return Msg.fail().add("mag", "用户头像不能为空");
//		}
//		if(!StringUtils.hasText(userBirthday)){
//			return Msg.fail().add("mag", "用户生日不能为空");
//		}
//		if(!StringUtils.hasText(userSex)){
//			return Msg.fail().add("mag", "用户性别不能为空");
//		}
//		if(!StringUtils.hasText(userIndustry)){
//			return Msg.fail().add("mag", "用户行业不能为空");
//		}
//		if(!StringUtils.hasText(userBrand)){
//			return Msg.fail().add("mag", "用户产品不能为空");
//		}
		ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(userid));
		if(selectByPrimaryKey == null){
			return Msg.fail().add("mag", "用户不存在");
		}
		selectByPrimaryKey.setUserName(userName);//用户名
		selectByPrimaryKey.setUserBrand(userBrand);//产品
		selectByPrimaryKey.setUserBirthday(userBirthday);//生日
		selectByPrimaryKey.setUserImg(userImg);//头像
		selectByPrimaryKey.setUserIndustry(userIndustry);//行业
		selectByPrimaryKey.setUserSex(userSex);//性别
		selectByPrimaryKey.setUserAddress(address);//地址
		selectByPrimaryKey.setUserLong(jing);//经度
		selectByPrimaryKey.setUserLat(wei);//纬度
		selectByPrimaryKey.setUserSpare2(speciality);//特长
		zsdUserService.updateByPrimaryKeySelective(selectByPrimaryKey);
		return Msg.success();
	}
	/**
	 * 手机端获取所有行业
	 */
	@RequestMapping(value = "/getAllIndustry")
	@ResponseBody
	public Msg getAllIndustry(){
		List<ZsdIndustry> list=zsdIndustryService.selectAll();
		if(list.size()==0||list==null) {
			return Msg.fail().add("mag", "行业表为空");
		}
		return Msg.success(list);
	}
	/**
	 * 手机端添加好友
	 */
	@RequestMapping(value = "/addFriend")
	@ResponseBody
	public Msg addFriend(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="friendid",required=false)String friendid,
			@RequestParam(value="group",required=false)String group){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不能为空");
		}
		if(!StringUtils.hasText(friendid)){
			return Msg.fail().add("mag", "好友id不能为空");
		}
		if(!StringUtils.hasText(group)){
			return Msg.fail().add("mag", "分组不能为空");
		}
		ZsdMaillist list = new ZsdMaillist();
		list.setUserId(Integer.valueOf(userid));
		//好友用户
		ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(friendid));
		if(selectByPrimaryKey == null) {
			return Msg.fail().add("mag", "好友不存在");
		}
		//自己
		ZsdUser zsduser = zsdUserService.selectByPrimaryKey(Integer.valueOf(userid));
		if(zsduser == null) {
			return Msg.fail().add("mag", "用户不存在");
		}
		//获取自己的好友
		List<ZsdMaillist> selectByUserid = zsdMaillistService.selectByfirend(list);
		if(selectByUserid.size()!=0&&selectByUserid!=null) {
			for (ZsdMaillist zsdMaillist : selectByUserid) {
				Integer friendUserId = zsdMaillist.getFriendUserId();
				Integer valueOf = Integer.valueOf(friendid);
				if(friendUserId == valueOf) {
					return Msg.fail().add("mag", "您已经添加过此用户");
				}
			}
		}
		//自己的
		ZsdMaillist zsdMaillist = new ZsdMaillist();
		zsdMaillist.setCreateTime(new Date());
		zsdMaillist.setFriendUserId(Integer.valueOf(friendid));
		zsdMaillist.setGrouping(group);
		zsdMaillist.setUserId(Integer.valueOf(userid));
		zsdMaillist.setRemark(selectByPrimaryKey.getUserName());
		zsdMaillistService.insertSelective(zsdMaillist);
		//好友的
		ZsdMaillist friend = new ZsdMaillist();
		friend.setCreateTime(new Date());
		friend.setFriendUserId(Integer.valueOf(userid));
		friend.setGrouping(group);
		friend.setUserId(Integer.valueOf(friendid));
		friend.setRemark(zsduser.getUserName());
		zsdMaillistService.insertSelective(friend);
		return Msg.success();
	}
	/**
	 * 手机端获取好友
	 */
	@RequestMapping(value = "/getFriend")
	@ResponseBody
	public Msg getFriend(@RequestParam(value="userid",required=false)String userid,
			@RequestParam(value="group",required=false)String group){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不能为空");
		}
		if(!StringUtils.hasText(group)){
			return Msg.fail().add("mag", "分组不能为空");
		}
		ZsdMaillist zsdMaillist = new ZsdMaillist();
		zsdMaillist.setUserId(Integer.valueOf(userid));
		zsdMaillist.setGrouping(group);
		List<ZsdMaillist> selectByUserid = zsdMaillistService.selectByfirend(zsdMaillist);
		List<Map<String,String>> list = new ArrayList<>();
		for (ZsdMaillist zsd : selectByUserid) {
			Integer friendUserId = zsd.getFriendUserId();
			String remark = zsd.getRemark();
			ZsdUser selectByPrimaryKey = zsdUserService.selectByPrimaryKey(Integer.valueOf(friendUserId));
			if(selectByPrimaryKey == null) {
				return Msg.fail().add("mag", "好友不存在");
			}
			Map<String, String> map = new HashMap<>();
			map.put("maillistid", zsd.getId().toString());//主键id
			map.put("name", remark);//备注
			map.put("friendid", selectByPrimaryKey.getUserImg());//好友头像
			list.add(map);
		}
		return Msg.success(list);
	}
	/**
	 * 手机端修改备注名称
	 */
	@RequestMapping(value = "/updateRemark")
	@ResponseBody
	public Msg updateRemark(@RequestParam(value="maillistid",required=false)String maillistid,
			@RequestParam(value="remark",required=false)String remark) {
		if(!StringUtils.hasText(maillistid)){
			return Msg.fail().add("mag", "通讯录id不能为空");
		}
		if(!StringUtils.hasText(remark)){
			return Msg.fail().add("mag", "备注不能为空");
		}
		ZsdMaillist selectByPrimaryKey = zsdMaillistService.selectByPrimaryKey(Integer.valueOf(maillistid));
		if(selectByPrimaryKey == null){
			return Msg.fail().add("mag", "好友找不到");
		}
		selectByPrimaryKey.setRemark(remark);
		zsdMaillistService.updateByPrimaryKeySelective(selectByPrimaryKey);
		return Msg.success();
	}
	/**
	 * 手机端删除好友
	 */
	@RequestMapping(value = "/deletefriend")
	@ResponseBody
	public Msg deletefriend(@RequestParam(value="maillistid",required=false)String maillistid){
		if(!StringUtils.hasText(maillistid)){
			return Msg.fail().add("mag", "通讯录id不能为空");
		}
		zsdMaillistService.deleteByPrimaryKey(Integer.valueOf(maillistid));
		return Msg.success();
	}
	/**
	 * 手机端获取关于我们
	 */
	@RequestMapping(value = "/getAboutUs")
	@ResponseBody
	public Msg getAboutUs(){
		ZsdAboutUs selectByPrimaryKey = zsdAboutUsService.selectByPrimaryKey(1);
		if(selectByPrimaryKey == null){
			return Msg.fail().add("mag", "关于我们为空");
		}
		return Msg.success(selectByPrimaryKey);
	}
	/**
	 * 手机端获取签到天数
	 */
	@RequestMapping(value = "/getSigninDay")
	@ResponseBody
	public Msg getSigninDay(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不能为空");
		}
		ZsdSignin selectByUserid = zsdSigninService.selectByUserid(Integer.valueOf(userid));
		if(selectByUserid == null) {
			return Msg.fail().add("mag", "签到表为空");
		}
		int differDayQty = differDayQty(selectByUserid.getTime(),new Date());
		if(differDayQty > 1) {
			selectByUserid.setAccumulateDay("0");//连续签到天数
			selectByUserid.setStatus("2");//签到状态  1已签到 2未签到
		}
		if(differDayQty == 1) {
			selectByUserid.setStatus("2");//签到状态  1已签到 2未签到
		}
		ZsdIntegral selectByUserid2 = zsdIntegralService.selectByUserid(Integer.valueOf(userid));
		if(selectByUserid2 == null){
			return Msg.fail().add("mag", "积分表为空");
		}
		zsdSigninService.updateByPrimaryKeySelective(selectByUserid);
		Map<String, String> map = new HashMap<>();
		map.put("signinday", selectByUserid.getAccumulateDay());
		map.put("signinstatus", selectByUserid.getStatus());
		map.put("signinintegral",selectByUserid2.getSignscore().toString());
		return Msg.success(map);
	}
	/**
	 * 手机端获取已签到的日期
	 */
	@RequestMapping(value = "/getSigninDayTime")
	@ResponseBody
	public Msg getSigninDayTime(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不能为空");
		}
		List<ZsdSigninrecord> selectByUserid2 = zsdSigninrecordService.selectByUserid(Integer.valueOf(userid));
		List<Map<String,String>>list = new ArrayList<>();
		if(selectByUserid2.size() != 0&&selectByUserid2 != null) {
			for (ZsdSigninrecord zsdSigninrecord : selectByUserid2) {
				Date time = zsdSigninrecord.getTime();
				Map<String, String> map = new HashMap<>();
				map.put("time", time.toString());
				list.add(map);
			}
			return Msg.success(list);
		}else {
			return Msg.success();
		}
	}
	/**
	 * 手机端签到
	 */
	@RequestMapping(value = "/signin")
	@ResponseBody
	public Msg signin(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不能为空");
		}
		ZsdSignin selectByUserid = zsdSigninService.selectByUserid(Integer.valueOf(userid));
		if(selectByUserid == null) {
			return Msg.fail().add("mag", "签到表为空");
		}
		int differDayQty = differDayQty(selectByUserid.getTime(),new Date());
		if(differDayQty == 0) {
			return Msg.fail().add("mag", "您已经签到过了");
		}
		String accumulateDay = selectByUserid.getAccumulateDay();
		Integer valueOf = Integer.valueOf(accumulateDay);
		Integer integer = valueOf+1;
		selectByUserid.setAccumulateDay(integer.toString());
		selectByUserid.setStatus("1");//已签到
		selectByUserid.setTime(new Date());//签到时间
		zsdSigninService.updateByPrimaryKeySelective(selectByUserid);
		//添加签到记录
		ZsdSigninrecord zsdSigninrecord = new ZsdSigninrecord();
		zsdSigninrecord.setTime(new Date());
		zsdSigninrecord.setSigninintegral(10.0);
		zsdSigninrecord.setUserId(Integer.valueOf(userid));
		zsdSigninrecordService.insertSelective(zsdSigninrecord);
		//添加积分明细
		ZsdIntegralDetails zsdIntegralDetails = new ZsdIntegralDetails();
		zsdIntegralDetails.setCreateTime(new Date());
		zsdIntegralDetails.setStatus("1");
		zsdIntegralDetails.setIntegral(10);
		zsdIntegralDetails.setUserId(Integer.valueOf(userid));
		zsdIntegralDetailsService.insertSelective(zsdIntegralDetails);
		//积分表添加积分
		ZsdIntegral selectByUserid2 = zsdIntegralService.selectByUserid(Integer.valueOf(userid));
		if(selectByUserid2 == null) {
			return Msg.fail().add("mag", "积分表为空");
		}
		Integer integral = selectByUserid2.getIntegral();
		selectByUserid2.setIntegral(integral+10);
		Integer signscore = selectByUserid2.getSignscore();
		selectByUserid2.setSignscore(signscore+10);
		zsdIntegralService.updateByPrimaryKeySelective(selectByUserid2);
		return Msg.success();
	}
	public static int differDayQty(Date Date1,Date Date2){
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.clear();
		calendar.setTime(Date1);
		int day1 = calendar.get(java.util.Calendar.DAY_OF_YEAR);
		int year1 = calendar.get(java.util.Calendar.YEAR);
		calendar.setTime(Date2);
		int day2 = calendar.get(java.util.Calendar.DAY_OF_YEAR);
		int year2 = calendar.get(java.util.Calendar.YEAR);
		if(year1 == year2){//同一年
			return day2-day1;
		}else if(year1<year2){//Date1<Date2
			int days = 0;
			for (int i = year1; i < year2; i++) {
				if(i%4 == 0 && i%100!=0 || i%400 == 0){//闰年
					days += 366;
				}else {
					days += 365;
				}
			}
			return days+(day2 - day1);
		}else{//Date1>Date2
			int days = 0;
			for (int i = year2; i < year1; i++) {
				if(i%4 == 0 && i%100!=0 || i%400 == 0){
					days += 366;
				}else {
					days += 365;
				}
			}
			return 0-days+(day2-day1);
		}
	}
	//获取前一天
	public static Date getUpDay(Date dt) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		dt = calendar.getTime();
		return dt;
	}
	/**
	 * 手机端获取积分明细
	 */
	@RequestMapping(value = "/getZsdIntegralDetails")
	@ResponseBody
	public Msg getZsdIntegralDetails(@RequestParam(value="userid",required=false)String userid){
		if(!StringUtils.hasText(userid)){
			return Msg.fail().add("mag", "用户id不能为空");
		}
		ZsdIntegralDetails zsdIntegralDetails = new ZsdIntegralDetails();
		zsdIntegralDetails.setUserId(Integer.valueOf(userid));
		List<ZsdIntegralDetails> selectByZsdIntegralDetails = zsdIntegralDetailsService.selectByZsdIntegralDetails(zsdIntegralDetails);
		if(selectByZsdIntegralDetails.size()!=0&&selectByZsdIntegralDetails!=null) {
			return Msg.success(selectByZsdIntegralDetails);
		}else {
			return Msg.success();
		}
	}
	
	/*
	 * 查找附近的工人
	 */
	@RequestMapping(value = "/getNearbyUser")
	@ResponseBody
	public Msg getNearbyUser (@RequestParam(value="lon",required=false)String lon,
			@RequestParam(value="lat",required=false)String lat) {
		//33.958887 118.302416 
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("lon", lon);
		map.put("lat", lat);
		List<ZsdUser> list=zsdUserService.selectByLog(map);
		List<ZsdUser> users=new ArrayList<ZsdUser>();
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getUserSpare()==0) {
					users.add(list.get(i));
				}
			}
			return Msg.success(users);
		}else {
			return Msg.fail().add("msg", "暂未结果");
		}
		
	}
	
	/*
	 *首页附近的企业
	 */
	@RequestMapping(value = "/getNearbyBusiness")
	@ResponseBody
	public Msg getNearbyBusiness(@RequestParam(value="lon",required=false)String lon,
			@RequestParam(value="lat",required=false)String lat) {
		//33.958887 118.302416 
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("lon", lon);
				map.put("lat", lat);
				List<ZsdUser> list=zsdUserService.selectByLog(map);
				List<ZsdUser> users=new ArrayList<ZsdUser>();
				if(list.size()>0) {
					for(int i=0;i<list.size();i++) {
						if(list.get(i).getUserSpare()==1) {
							users.add(list.get(i));
						}
					}
					return Msg.success(users);
				}else {
					return Msg.fail().add("msg", "暂未结果");
				}
	}
	/*************************************************************用户后台管理*****************************************/
	/**
	 * 跳转普通用户列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/zsdUserList";
	}	
	
	/**
	 * 跳转会员列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toMamberList")
	public String toMamberList(HttpServletRequest request,Model model) {
		return "jsp/zsdUserMamberList";
	}	
	
	/**
	 * 跳转超级会员列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toSuperList")
	public String toSuperList(HttpServletRequest request,Model model) {
		return "jsp/zsdUserSuperList";
	}	
}
