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
  <title>新闻详情</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form">
	<input type="hidden" id="dbImg" value="${obj.dbImg }">
	  <div class="layui-form-item">
	    <label class="layui-form-label">新闻标题：</label>
	    <div class="layui-input-block" id="dbTitile">
	      ${obj.dbTitile}
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">新闻内容：</label>
	    <div class="layui-input-block" id="dbText">
	      ${obj.dbText}
	    </div>
	  </div>
	  
	 
	  <div class="layui-form-item">
	    <label class="layui-form-label">新闻图片：</label>
	    <div class="layui-upload-list"  id="productImg_one">
      </div>
	  </div>
	  
	  
	
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">创建时间：</label>
	    <div class="layui-input-block" id="createDate">
	      ${obj.createDate}
	    </div>
	  </div>
	 
	</form>
	<script src="http://apps.bdimg.com/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script>
	   $(function(){
		 var array=$("#dbImg").val().split(',');
		 for(var i in array){
			 if(array[i]){
				 var item=$("<img  width='90px' height='95px' class='layui-upload-img' src='${path }"+array[i]+"'/>");
				 $("#productImg_one").append(item); 
			 }
		 }
	});
	</script>
	
	
	
</body>
</html>