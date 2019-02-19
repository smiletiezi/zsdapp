package com.py.zsdApp.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.google.common.collect.Maps;
import com.py.zsdApp.annotation.SystemControllerLog;
import com.py.zsdApp.entity.ZsdNews;
import com.py.zsdApp.service.ZsdNewsService;
import com.py.zsdApp.utils.Msg;
import com.py.zsdApp.utils.Utils;

@Controller
@RequestMapping("/zsdnews")
public class ZsdNewsController extends BaseController{

	@Autowired
	private ZsdNewsService zsdNewsService;
	
	/**
	 * 跳转新闻列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toList")
	public String toList(HttpServletRequest request,Model model) {
		return "jsp/zsdNewsList";
	}	
	
	/**
	 * 获取新闻列表数据
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description="查询新闻列表")
	@RequestMapping(value = "toListData")
	@ResponseBody
	public Map<String,Object> toListData(HttpServletRequest request) {
		String title=request.getParameter("dbTitile");
		ZsdNews news=new ZsdNews();
		if(StringUtil.isNotEmpty(title)) {
			news.setDbTitile(title);
		}
		//返回map
				Map<String,Object> resultMap = Maps.newHashMap();
				//获取分页和排序条件
				LayerPage(request);
				//排序插件
				PageHelper.orderBy("id ASC");
				//分页插件
				Page<?> page = PageHelper.startPage(pageNum, pageSize);
		//调用service
		
		List<ZsdNews> list =zsdNewsService.selectByTitle(news);
		//返回layui数据
		resultMap.put("code", 0);
		resultMap.put("msg", "查询成功");
		resultMap.put("count", page.getTotal());
		resultMap.put("data", list);
		return resultMap;
	}
	
	/**
	 * 页面跳转 新增或修改跳转form
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toForm")
	public String getUserList(HttpServletRequest request,Model model) {
		String path=Utils.getProperties("attachment_path");
		int id = 0;
		try {
			id= Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}
		if(id > 0) {
			ZsdNews news = zsdNewsService.selectByPrimaryKey(id);
			model.addAttribute("obj", news);
			model.addAttribute("path", path);
		}
		return "jsp/zsdNewsForm";
	}
	
	/**
	 * 新增新闻  
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@SystemControllerLog(description="新增新闻")
	@RequestMapping(value="insert")
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request,@ModelAttribute("obj") ZsdNews news) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		news.setCreateDate(format.format(new Date()));
		zsdNewsService.insertSelective(news);
		resultMap.put("type", "add");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 修改新闻 
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@SystemControllerLog(description="修改新闻")
	@RequestMapping(value="set")
	@ResponseBody
	public Map<String, Object> set(HttpServletRequest request,@ModelAttribute("obj") ZsdNews news) {
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		news.setCreateDate(format.format(new Date()));
		zsdNewsService.updateByPrimaryKeySelective(news);
		resultMap.put("type", "update");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/**
	 * 根据ID查询新闻详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@SystemControllerLog(description="查询新闻详情")
	@RequestMapping("toDetails")
	public String toDetails(HttpServletRequest request, Model model) {
		String path=Utils.getProperties("attachment_path");
		Integer id=Integer.parseInt(request.getParameter("id"));
		ZsdNews news =zsdNewsService.selectByPrimaryKey(id);
		model.addAttribute("obj",news);
		model.addAttribute("path",path);
		return "jsp/zsdNewsDetails";
	}
	
	/**
	 * 删除新闻 
	 * @param request
	 * @param commodityClassification
	 * @return
	 */
	@SystemControllerLog(description="删除新闻")
	@RequestMapping(value="redelete")
	@ResponseBody
	public Map<String, Object> redelete(HttpServletRequest request) {
		Integer id=Integer.parseInt(request.getParameter("id"));
		//返回map
		Map<String, Object> resultMap = Maps.newHashMap();
		zsdNewsService.deleteByPrimaryKey(id);
		resultMap.put("type", "delete");
		resultMap.put("code", "success");
		return resultMap;
	}
	
	/*app端 获取新闻列表*/
	@ResponseBody
	@RequestMapping(value="/list")
	public Msg list(){
		ZsdNews record=new ZsdNews();
		List<ZsdNews> news=zsdNewsService.selectByTitle(record);
		return Msg.success(news);
	}
	
	/*根据id查询新闻详情*/
	@ResponseBody
	@RequestMapping(value="/detail")
	public Msg detail(@RequestParam(value="id",required=false)Integer id){
		if(id != null) {
			ZsdNews news=zsdNewsService.selectByPrimaryKey(id);
			return Msg.success(news);
		}else {
			return Msg.fail().add("msg", "请选择新闻");
		}
		
	}
	
}
