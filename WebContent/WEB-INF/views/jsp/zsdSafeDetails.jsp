<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>保单详情</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form">
	  <div class="layui-form-item">
	    <label class="layui-form-label">用户ID：</label>
	    <div class="layui-input-block" id="userid">
	      ${obj.userid}
	    </div>
	  </div>
	  
	 
	  <div class="layui-form-item">
	    <label class="layui-form-label">身份证人像面：</label>
	    <img  width="90px" height="95px" class="layui-upload-img" id="Img_one" src="${path }${obj.imgOne}">
      </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">身份证国徽面：</label>
	    <img  width="90px" height="95px" class="layui-upload-img" id="Img_two" src="${path }${obj.imgTwo}">
      </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">收货地址：</label>
	    <div class="layui-input-block" id="address">
	      ${obj.address}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">收货人电话：</label>
	    <div class="layui-input-block" id="phone">
	      ${obj.phone}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">保单：</label>
	    <div class="layui-input-block" id="warranty">
	    <img  width="90px" height="95px" class="layui-upload-img" id="warranty" src="${path }${obj.warranty}">
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">物流单号：</label>
	    <div class="layui-input-block" id="warranty">
	      ${obj.remark}
	    </div>
	  </div>
	 
	</form>
	<script src="http://apps.bdimg.com/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script>
	</script>
	
	
	
</body>
</html>