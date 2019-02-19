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
  <title>详情</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form">
	  <div class="layui-form-item">
	    <label class="layui-form-label">保险名称：</label>
	    <div class="layui-input-block" id="dbTitile">
	      ${obj.name}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">保险金额：</label>
	    <div class="layui-input-block" id="dbText">
	      ${obj.money}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">详情介绍：</label>
	    <div class="layui-input-block" id="dbText">
	      ${obj.detail}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">温馨提示：</label>
	    <div class="layui-input-block" id="dbText">
	      ${obj.remark}
	    </div>
	  </div>
	</form>
	<script src="http://apps.bdimg.com/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
</body>
</html>