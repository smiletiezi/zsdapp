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
  <title>订单详情</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form">
	<input type="hidden" id="img" value="${obj.img }">
	  <div class="layui-form-item">
	    <label class="layui-form-label">订单标题：</label>
	    <div class="layui-input-block" id="title">
	      ${obj.title}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">订单类型：</label>
	    <div class="layui-input-block" id=ordertypeid>
	    <c:forEach items="${types}" var="type">
	    <c:if test="${type.id==obj.ordertypeid }">
	    ${type.name }
	    </c:if>
	    </c:forEach>
	    </div>
	  </div>
	  
	   <div class="layui-form-item">
	    <label class="layui-form-label">订单图片：</label>
	    <div class="layui-upload-list"  id="orderImg">
      </div>
	  </div>
	  
	   <div class="layui-form-item">
	    <label class="layui-form-label">订单地址：</label>
	    <div class="layui-input-block" id="address">
	      ${obj.address}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">订单详情：</label>
	    <div class="layui-input-block" id="detail">
	      ${obj.detail}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">订单金额：</label>
	    <div class="layui-input-block" id="detail">
	      ${obj.price}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">联系电话：</label>
	    <div class="layui-input-block" id="detail">
	      ${obj.phone}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">价格状态：</label>
	    <div class="layui-input-block" id="priceType">
	      <c:if test="${obj.priceType == '1'}">面议</c:if>	
	      <c:if test="${obj.priceType == '2'}">标价</c:if>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">发布时间：</label>
	    <div class="layui-input-block" id="createTime">
	      <fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">订单状态：</label>
	    <div class="layui-input-block" id="productSale">
	     <c:if test="${obj.orderStatus == '1'}">待接单</c:if>	
	      <c:if test="${obj.orderStatus == '2'}">验资中</c:if>
	      <c:if test="${obj.orderStatus == '3'}">代签收</c:if>
	      <c:if test="${obj.orderStatus == '4'}">已成交</c:if>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">正文：</label>
	    <div class="layui-input-block" id="orderText">
	      ${obj.orderText}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">押金：</label>
	    <div class="layui-input-block" id="orderText">
	      ${obj.orderText}
	    </div>
	  </div>
	  
	   <div class="layui-form-item">
	    <label class="layui-form-label">经度：</label>
	    <div class="layui-input-block" id="orderLong">
	      ${obj.orderLong}
	    </div>
	  </div>
	  
	   <div class="layui-form-item">
	    <label class="layui-form-label">纬度：</label>
	    <div class="layui-input-block" id="orderLat">
	      ${obj.orderLat}
	    </div>
	  </div>
	  
	  
	
	</form>
	<script src="http://apps.bdimg.com/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script src="${ctx }/static/zoomify/zoomify.min.js"></script>
	<script src="${ctx }/static/zoomify/zoomify.min.css"></script>
	<script>
	   $(function(){
		 var array=$("#img").val().split(',');
		 for(var i in array){
			 if(array[i]){
				 var item=$("<img  width='90px' height='95px' class='layui-upload-img' src='${path }"+array[i]+"'/>");
				 $("#orderImg").append(item); 
			 }
		 }
	});
	</script>
	
	
	
</body>
</html>