<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
	<c:when test="${obj == null}">
		<c:set var="url" value="${ctx }/zsdindustry/insert"></c:set>
		<c:set var="text" value="新增"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="url" value="${ctx }/zsdindustry/update"></c:set>
		<c:set var="text" value="修改"></c:set>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>${text }行业</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">                   
	<form class="layui-form" id="form" action="${url}" commandName="obj" lay-filter="formFilter">
	  <div class="layui-form-item">
	    <label class="layui-form-label"><span style="color:red;">*</span>行业名称</label>
	    <div class="layui-input-block">
	      <input type="text" name="name" lay-verify="name" autocomplete="off" placeholder="请输入行业名称" class="layui-input" value="${obj.name }">
	    </div>
	  </div>
	  
	  
	  <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">备注</label>
	    <div class="layui-input-block">
	      <textarea placeholder="请输入内容,不超过200个字" class="layui-textarea" name="remark" lay-verify="remark">${obj.remark }</textarea>
	    </div>
	  </div>
	  <input type="hidden" name="id" value="${obj.id }">
	  <button class="layui-btn" lay-submit lay-filter="submmitFilter" style="display: none;" id="sunmitbtn"></button>
	</form>
	     
	<!-- JS -->
	<script src="http://apps.bdimg.com/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script src="${ctx }/static/lib/jquery-form.js"></script>
	<script>
	var parentIndex;
	layui.use(['form'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  //表单验证
		  form.verify({
			  name: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length == 0){
			  		return '用户名不能为空';
			  	}
			  	if(value.length > 20){
			  		return '用户名长度不能大于20个字'
			  	}
			    if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
			      return '用户名不能有特殊字符';
			    }
			  },
			  remark: function(value, item){ //value：表单的值、item：表单的DOM对象
				value = $.trim(value);
			  	if(value.length > 200){
			  		return '备注长度不能大于200个字'
			  	}
			  }
		  });
		  //监听提交
		  form.on('submit(submmitFilter)', function(data){
			  $("#form").ajaxSubmit({
		    		url: "${url}",
		    		type: "post",
		    		success: function(result) {
		    			var code = result.code;
		    			var type = result.type;
		    			if(code == 'success'){
		    				if(type == 'add'){
		    					layer.msg('添加成功', {
		    		    			  icon: 6
		    		    			  ,time: 1500
		    		    		});
		    					
		    				}else{
		    					layer.msg('修改成功', {
		    		    			  icon: 6
		    		    			  ,time: 1500
		    		    		});
		    					
		    				}
		    				setTimeout(function(){
		    					parent.conditionReset();
		    					parent.layer.close(parentIndex);
		    				}, 1500);
		    			}
		    		},
		    		error: function() {
		    			layer.msg('提交失败', {
			    			  icon: 5
			    			  ,time: 1500
			    		});
		    		}
		    	});
			    return false;
		  });
		  
	});
	//模拟按钮提交 
	function submitForm(index){
		parentIndex = index;
		$("#sunmitbtn").click();
	}
	</script>

</body>
</html>