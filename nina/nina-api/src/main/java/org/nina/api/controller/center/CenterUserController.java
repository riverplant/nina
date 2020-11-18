package org.nina.api.controller.center;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.nina.api.controller.BaseController;
import org.nina.commons.utils.CookieUtils;
import org.nina.commons.utils.DateUtil;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.UserInfo;
import org.nina.resources.FileUploadResources;
import org.nina.service.center.CenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户中心接口
 * 
 * @author riverplant
 *
 */
@RestController
@Api(value = "center-用户中心", tags = "用户中心展示的相关接口")
@RequestMapping("userInfo")
public class CenterUserController extends BaseController{
    @Autowired private CenterUserService centerUserService;
	@Autowired private FileUploadResources fileUploadResources;
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息",httpMethod = "PUT")
	@PutMapping("userInfo/{userId:\\d+}")
	public NinaJsonResult userInfo(
			@ApiParam(name = "userId",value = "用户ID", required = true)
			@PathVariable Long userId,
			@RequestBody @Valid UserInfo userInfo,
			HttpServletRequest request,
			HttpServletResponse response,
			BindingResult result) {
    	if(result.hasErrors()) {//判断是否有错误的验证信息
    		return NinaJsonResult.erorException(result.toString());
    	}
    	UserInfo user = centerUserService.updateUserInfo(userId, userInfo);
		CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user),true);
    	return NinaJsonResult.ok(user);
	}
    
    @ApiOperation(value = "用户头像修改", notes = "用户头像修改",httpMethod = "POST")
   	@PostMapping("uploadFace/{userId:\\d+}")
   	public NinaJsonResult changeUserFace(
   			@ApiParam(name = "userId",value = "用户ID", required = true)
   			@PathVariable Long userId,
   			HttpServletRequest request,
   			HttpServletResponse response,
   			@ApiParam(name = "file",value = "用户头像", required = true)
   			MultipartFile file) {
       //定义头像保存的地址
    	String desPath = fileUploadResources.getImageUserFaceLocation();
    	
		//开始文件上传
		if(file != null && StringUtils.isNoneBlank(file.getOriginalFilename())) {
			//获取文件后缀名
			String extention = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
			//限制上传图片的格式，避免黑客攻击
			if(!extention.equalsIgnoreCase("png") &&
					!extention.equalsIgnoreCase("jpg") &&	
					!extention.equalsIgnoreCase("jpeg")) {
				return NinaJsonResult.erorMsg("图片格式不正确");
			}
			
			//在路径下为每一个用户增加一个userid用于区分不同的用户上传
	    	String uploadPathPrefix = userId+"_"+new Date().getTime()+"."+extention;
			File localfile = new File(desPath,  uploadPathPrefix);
			//创建文件路径
			if(localfile.getParentFile() != null) {
				localfile.getParentFile().mkdirs();
			}
//			FileOutputStream fileOutputStream = new FileOutputStream(localfile);
//			InputStream inputStream = file.getInputStream();
//			IOUtils.copy(inputStream, fileOutputStream);
			try {
				file.transferTo(localfile);
				String imageServiceUrl = fileUploadResources.getImageServiceUrl();
				//由于浏览器可能存在缓存，所以这里加入一个时间戳
				String finalUserRul = imageServiceUrl +"/"+ uploadPathPrefix+"?t="+DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
				UserInfo userResult = centerUserService.updateUserFace(userId,finalUserRul);
				CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult));
				//TODO 后续会增加令牌Token并且整合入redis
				return NinaJsonResult.ok(userResult);
			} catch (IllegalStateException e) {
				return NinaJsonResult.erorException(e.getMessage());
			} catch (IOException e) {
				return NinaJsonResult.erorException(e.getMessage());
			}
		} else {
			return NinaJsonResult.erorMsg("文件不能为空");
		}
   	}
       
}
