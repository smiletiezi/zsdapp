package com.py.zsdApp.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.pagehelper.StringUtil;
import com.py.zsdApp.entity.ZsdSafe;
import com.py.zsdApp.entity.ZsdUser;
import com.py.zsdApp.service.ZsdSafeService;
import com.py.zsdApp.service.ZsdUserService;
import com.py.zsdApp.utils.AlipayConfig;
import com.py.zsdApp.utils.CommonUtil;
import com.py.zsdApp.utils.Msg;
import com.py.zsdApp.utils.SMSBean;




@Controller
@RequestMapping("/alipay")
public class PayAlipayPreController {
	@Autowired
	private ZsdSafeService zsdSafeService;
	@Autowired
	private ZsdUserService zsdUserService;
	/**创建支付接口
	*@param phonenum      充值人
	*@param tradeMoney    充值money(RMB)  
	*@param userid    用户
	*@param type    交易类型 1购买意外险 2会员充值 3订单支付 4平台充值
	*@throws AlipayApiException  ModelAndView
	 * @throws IOException 
	 */
	 @ResponseBody
	 @RequestMapping(value="/api/alipay/createOrder")
	 public Msg alipay(HttpServletRequest request) throws AlipayApiException, IOException{
		 Msg msg=Msg.success();
		 String orderStr = "";
		 String type=request.getParameter("type");
	   try{
		   if(StringUtil.isNotEmpty(type) && type.equals("1")) {
			   double tradeMoney = 0d;
				try {
					 tradeMoney = Double.parseDouble(request.getParameter("tradeMoney"));
				} catch (Exception e) {
					 tradeMoney = 0d;
				}
				int userid = 0;
				try {
					userid = Integer.parseInt(request.getParameter("userid"));
				} catch (Exception e) {
					userid = 0;
				}
			   //购买意外险
			   //实例化客户端  
		        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay 
		        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();  
		        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。  
		        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();  
		        model.setBody("支付宝支付"+tradeMoney +"元");                        							
		        model.setSubject("支付宝支付");                  											
		        model.setOutTradeNo(String.valueOf(userid));          			
		        model.setTimeoutExpress("30m");   															
		        model.setTotalAmount(String.valueOf(tradeMoney));         									
		        model.setProductCode("QUICK_MSECURITY_PAY");         										
		        ali_request.setBizModel(model);  
		        ali_request.setNotifyUrl(AlipayConfig.notify_url);          								//回调地址  
		        AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);  
		        orderStr = response.getBody();
		        System.err.println(orderStr);                                								//就是orderString 可以直接给客户端请求，无需再做处理。  
		   }else if (StringUtil.isNotEmpty(type) && type.equals("2")) {
			   //会员充值 交保证金
		   }else if(StringUtil.isNotEmpty(type) && type.equals("3")) {
			   //订单支付
		   }else if(StringUtil.isNotEmpty(type) && type.equals("4")){
			   //平台充值
		   }
		    
	        msg.setCode(100);
	        msg.setMsg("处理成功");
	        msg.add("result", orderStr);
	        }catch(Exception e){
	       	    msg.setCode(200);
		        msg.setMsg("处理失败");
		        msg.add("result", orderStr);
	        }
	    return msg;
	}
	 
	 /**
	  * 支付宝支付成功后.回调该接口
	  * @param request
	  * @return
	  * @throws IOException
	  */  
	 @ResponseBody
	 @RequestMapping(value="/alipayNotify")
	 public String notify(HttpServletRequest request,HttpServletResponse response) throws IOException {  
		 Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			//String out_trade_no = request.getParameter("out_trade_no");//订单表id
			String tradeStatus = request.getParameter("trade_status");//交易状态
			//String transaction_id = request.getParameter("transaction_id");//支付宝交易流水单号
			boolean signVerified = false;
			try {
				signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
						AlipayConfig.SIGNTYPE);
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
	     if (signVerified) {    //验签通过   
	         if(tradeStatus.equals("TRADE_SUCCESS")) {    //只处理支付成功的订单: 修改交易表状态,支付成功
	        	 System.out.println("验签通过");
	        	 //购买意外险成功 修改意外险状态
	        	Integer userid=Integer.parseInt(request.getParameter("out_trade_no"));
	        	ZsdSafe safe=new ZsdSafe();
	        	safe.setUserid(userid);
	        	List<ZsdSafe> zsdSafe=zsdSafeService.selectByExample(safe);
	        	zsdSafe.get(0).setType("1");
	        	int returnResult = zsdSafeService.updateByPrimaryKeySelective(zsdSafe.get(0));
	        	 if(returnResult > 0){
	                  return "success";
	             }else{
	                  return "fail";
	             }
	         }else{
	             return "fail";
	         }
	     } else {  //验签不通过   
	         System.err.println("验签失败");
	         return "fail";
	     }
	 }
	
	
	 /**
	  * alipay.fund.trans.toaccount.transfer(单笔转账到支付宝账户接口)
	  * 参考文档：https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer/
	  * @author wb-wly251833
	  *
	  */
	 @ResponseBody
	 @RequestMapping(value="api/alipayFundTransToaccountTransfer",method={RequestMethod.POST})
	 public Msg alipayFundTransToaccountTransfer( @RequestParam("phonenum")String phonenum,
			 @RequestParam("tradeMoney")Double tradeMoney,
			 @RequestParam("account")String account) throws AlipayApiException{
		 
		 	if(account == null || "".equals(account.trim())) {
		 		return Msg.fail().add("msg", "请填写支付宝账号");
		 	}
		 	if(tradeMoney == null || tradeMoney == 0) {
		 		return Msg.fail().add("msg", "请填写提现金额");
		 	}
		 
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gateway_url, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
			
			SMSBean smsBean = (SMSBean) CommonUtil.MSG_MAP.get(phonenum);
			ZsdUser user = zsdUserService.selectByPrimaryKey(smsBean.getId());
			
			if(user.getUserBalance() < tradeMoney) {
				return Msg.fail().add("msg", "您的余额只有"+user.getUserBalance()+"元");
			}
			
			
			//商户转账唯一订单号
			model.setOutBizNo(String.valueOf((Math.random()*9+1)*100000));
			//收款方账户类型。 
			//1、ALIPAY_USERID：pid ,以2088开头的16位纯数字组成。 
			//2、ALIPAY_LOGONID：支付宝登录号(邮箱或手机号)
			model.setPayeeType("ALIPAY_LOGONID");
			//收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
			model.setPayeeAccount(account);
			//测试金额必须大于等于0.1，只支持2位小数，小数点前最大支持13位
			if(AlipayConfig.falg) {
				model.setAmount(String.valueOf(tradeMoney));
			}else {
				model.setAmount("0.1");
			}
			//当付款方为企业账户且转账金额达到（大于等于）50000元，remark不能为空。
			model.setRemark("提现");
			request.setBizModel(model);
			
			try {
				AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
				System.out.println(response.getBody());	
				if(!response.isSuccess()){
//					user.setUserBalance(user.getUserBalance() + tradeMoney);
//					userService.updateByPrimaryKeySelective(user);
//					tradingRecord.setTradingRecordStatus(0);
//					tradingRecordService.updateByPrimaryKeySelective(tradingRecord);
					return Msg.fail().add("msg", response.getSubMsg());
				}
			} catch (Exception e) {
				return Msg.fail().add("msg", "提现失败");
			}
			
			user.setUserBalance(user.getUserBalance() - tradeMoney);
			try {
				zsdUserService.updateByPrimaryKeySelective(user);
			} catch (Exception e) {
			}
			return Msg.success();
	 }
	
	 /**
	  * alipay.fund.trans.order.query(查询转账订单接口)
	  * 商户可通过该接口查询转账订单的状态、支付时间等相关信息，主要应用于B2C转账订单查询的场景
	  * 普通单笔转账到支付宝用这个没意义，查不到转账金额。order_fee这个参数 是对银行卡才用到的，如果转到支付宝余额的话 是为0
	  * @author wb-wly251833
	  *
	  */
 	public void alipayFundTransOrderQuery() throws AlipayApiException {
 		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gateway_url, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
 		AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
 		AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
 		//	商户转账唯一订单号
//	 		model.setOutBizNo("2017081015423214234321");
 		//支付宝转账单据号：和商户转账唯一订单号不能同时为空。二选一传入
 	   	model.setOrderId("20170810110070001500580000006005");
 		request.setBizModel(model);
 		AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
 		System.out.println(response.getBody());
 		
 		if(response.isSuccess()){
 		System.out.println("调用成功");
 		} else {
 		System.out.println("调用失败");
 		}
 	}
}
