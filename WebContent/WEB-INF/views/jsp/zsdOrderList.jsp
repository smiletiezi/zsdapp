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
  <title>layui</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx}/static/css/admin-reset.css" media="all">
</head>
<body class="contPadding">
<!-- 搜索 -->
	<div class="search">
		<form class="layui-form"> 
		        订单标题：
			<div class="layui-inline">
				<input class="layui-input" name="title" id="title" autocomplete="off" placeholder="请输入订单标题">
			</div>
		  	<button class="layui-btn" type="button" onclick="conditionSearch();">搜索</button>
		  	<button class="layui-btn" type="button" onclick="conditionReset();">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	<div class="layui-inline">
				<select id="edit_exam_school">
                   <option value="">请选择</option>
                   <option value="1">发布订单找工人</option>
                   <option value="2">广告位招租</option>
				   <option value="3">广告器材转让</option>
				   <option value="4">安装工具</option>
				   <option value="5">安装知识与技巧</option>
				   <option value="6">装饰材料</option>
                  </select>
			</div>
			<button class="layui-btn" type="button" onclick="search();">订单类型筛选</button>
	  	</form>
	</div>
	<!-- 顶部按钮 -->
	<div class="layui-btn-container tbtn">
	  <button class="layui-btn " onclick="refresh();">刷新列表</button>
	</div>
	<table class="layui-hide" id="tableId" lay-filter="tableFilter"></table>
	<!-- 右边按钮 -->
	<script type="text/html" id="rbtn">
      <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
	</script>
	<!-- 订单类型转化模板  -->
	<script id="orderTypeConversion" type="text/html">  
    	{{orderTypeToChinese(d.orderStatus)}}   
    </script>
	<!-- JS -->
	<script src="http://apps.bdimg.com/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
	<script>
	//加载table模块
	var table,form;
	layui.use(['table','form'], function(){
	  table = layui.table;
	  form = layui.form;
	  //加载表格数据
	  table.render({
	    elem: '#tableId'
	    ,url:'${ctx}/order/toListData'
	    ,cellMinWidth: 80
	    ,page: true
	    ,cols: [[
	      {type:'checkbox'}
	      ,{field:'id', title: 'ID', sort: true}
	      ,{field:'title', title: '订单标题'}
	      ,{field:'address', title: '地址'} 
	      ,{field:'price', title: '订单金额'}
	      ,{field:'phone', title: '联系电话'}
	      ,{field:'orderStatus', title: '订单状态',templet:'#orderTypeConversion'}
	      ,{title:'操作',align:'center', toolbar: '#rbtn'}
	    ]]
	  });
	  
	  //监听右边按钮
	  table.on('tool(tableFilter)', function(obj){
	     var data = obj.data //获得当前行数据
	    ,layEvent = obj.event; //获得 lay-event 对应的值
	    if(layEvent === 'detail'){
		      doDetails('订单详情',data.id)
		    }
	  });
	}); 
	
	//刷新页面
	function refresh(){
		 table.reload('tableId', {
		    });
	}
	
	//条件查询
	function conditionSearch(){
		var title = $("#title").val();
	    table.reload('tableId', {
	      page: {
	        curr: 1 //重新从第 1 页开始
	      }
	      ,where: {
	    	  title: title,
	      }
	    });
	}
	
	//根据发布类型查找
	function search(){
		var orderTypeId = $("#edit_exam_school").val();
		 table.reload('tableId', {
		      page: {
		        curr: 1 //重新从第 1 页开始
		      }
		      ,where: {
		    	  orderTypeId: orderTypeId,
		      }
		    });
	}
	
	//重置
	function conditionReset(){
		$("#title").val('');
    	//刷新下拉菜单
    	form.render('select','conditionTypeFilter');
    	table.reload('tableId', {
  	      page: {
  	        curr: 1 //重新从第 1 页开始
  	      }
  	      ,where: {
  	    	title: '',
  	      }
  	    });
	}
	
	//details弹出框
	function doDetails(title,id){
		layer.open({
			  type: 2,
			  title: title,
			  shade: 0.8,
			  maxmin: true,
			  area: ['100%','100%'],
			  content: '${ctx}/order/toDetails?id='+id
		 });
	}
	
	
	/********************************************************** 转换处理方法 **********************************************/
	function timestampToTime(timestamp){
		if(null == timestamp || timestamp.length == 0){
			return '';
		}
		var date = new Date(timestamp);
		var y = date.getFullYear();    
        var m = date.getMonth()+1;    
        m = m<10?'0'+m:m;    
        var d = date.getDate();    
        d = d<10?("0"+d):d;    
        var h = date.getHours();    
        h = h<10?("0"+h):h;    
        var M = date.getMinutes();    
        M = M<10?("0"+M):M;    
        return y+"-"+m+"-"+d+" "+h+":"+M;
	}
	
	function orderTypeToChinese(type){
		var text= '';
		switch(type)
		{
		case '1':
			text='代接单';
		  break;
		case '2':
			text='验资中';
		  break;
		case '3':
			text='代签收';
		  break;
		case '4':
			text='已成交';
		  break;
		}
		return text;
	}
	
	</script>
</body>
</html>