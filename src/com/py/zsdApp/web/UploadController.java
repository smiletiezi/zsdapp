package com.py.zsdApp.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.py.zsdApp.utils.Utils;


@Controller
@RequestMapping("upload")
public class UploadController extends BaseController {
	
	/**
	 * 异步上传文件带监听
	 * @param request
	 * @return
	 * @throws FileUploadException 
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFileMonitor(MultipartFile file) throws FileUploadException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		 if (null != file) {
	            String myFileName = file.getOriginalFilename();// 文件原名称
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            String fileName = sdf.format(new Date())+Integer.toHexString(new Random().nextInt()) +"."+ myFileName.
	                    substring(myFileName.lastIndexOf(".") + 1);  
	         // 文件保存目录路径
	    		String savePath = Utils.getProperties("file_upload_path");
	            File fileDir=new File(savePath);
	            if (!fileDir.exists()) { //如果不存在 则创建     
	                 fileDir.mkdirs();    
	             } 
	            String path=savePath+fileName;
	            File localFile = new File(path);  
	            try {
	                file.transferTo(localFile);
	                resultMap.put("state", "0");
	                resultMap.put("link", fileName);
	                return resultMap;
	            } catch (IllegalStateException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }else{
	            System.out.println("文件为空");
	        }
	        return null;
	    }
	
	
	/**
	 * 异步上传文件带监听
	 * @param request
	 * @return
	 * @throws FileUploadException 
	 */
	@RequestMapping("/uploadVideo")
	@ResponseBody
	public Map<String,Object> uploadVideo(HttpServletRequest request) throws FileUploadException{
		String name=request.getParameter("name");
		return uploadVideo(request,name);
	}
	
	/**
	 * 异步上传文件带监听
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws FileUploadException 
	 */
	@RequestMapping("/uploadPhoneFile")
	@ResponseBody
	public  Map<String,Object> uploadPhoneFile(String pic) throws Exception {
		return analysisPic(pic);
	}
}
