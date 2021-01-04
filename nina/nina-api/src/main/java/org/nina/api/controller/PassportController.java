package org.nina.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.CookieUtils;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.MD5Utils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.commons.utils.RedisOperator;
import org.nina.dto.UserInfo;
import org.nina.dto.UserInfo.UserListView;
import org.nina.dto.vo.ShopcartVO;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "注册登录", tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private RedisOperator redisOperator;
	
	@ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
	@GetMapping(value = "/isexist",produces = {"application/json; charset=UTF-8"})
	public NinaJsonResult usernameIsExist(@RequestParam String username) {
        //1.判断用户名是否为空
		if(StringUtils.isBlank(username)) {
			return NinaJsonResult.erorMsg("用户名不能为空");
		}
		boolean isExist = userService.queryUsernameIsExist(username);
		if(isExist) {
			return NinaJsonResult.erorMsg("用户名已经存在");
		}
		return NinaJsonResult.ok();
	}
	
	
	/**
	 * 
	 * @param info
	 * @param request:用于生成Cookie
	 * @param respnse
	 * @param result:校验结果
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
	@JsonView(UserListView.class)
	@PostMapping(value = "/login",produces = {"application/json; charset=UTF-8"})
	public NinaJsonResult login(@Valid @RequestBody UserInfo info,HttpServletRequest request, HttpServletResponse response,BindingResult result) throws Exception {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			return NinaJsonResult.erorMsg(result.toString());
		}
		String username = info.getUsername();
		String password = MD5Utils.getMD5Str(info.getPassword());
		boolean isExist = userService.queryUsernameIsExist(username);
		if(!isExist) {
			return NinaJsonResult.erorMsg("用户名不存在");
		}
		UserInfo resultData = userService.queryUserForLogin(username,password);
		if(resultData == null) {
			return NinaJsonResult.erorMsg("密码与用户名不匹配");
		}
		//设置浏览器端Cookies
		CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(resultData),true);
		
		//TODO:生成用户Token,存入redis会话
		//同步购物车数据
		synchShopcartData(resultData.getId(), request, response);
		return NinaJsonResult.ok(resultData);
	}
	/**
	 * 
	 * @param info
	 * @param request
	 * @param response
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户退出", notes = "用户退出登录", httpMethod = "POST")
	@PostMapping(value = "/logout",produces = {"application/json; charset=UTF-8"})
	public NinaJsonResult logOut(@RequestParam Long id ,HttpServletRequest request, HttpServletResponse response) throws Exception {

		//清除浏览器端用户相关Cookies
		CookieUtils.deleteCookie(request, response, "user");
		//TODO 用户退出登录.需要清空购物车
		CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);
		//分布会话中需要清除用户数据
		
		return NinaJsonResult.ok();
	}
	
	/**
	 * 
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
	@PostMapping
	public NinaJsonResult register(@Valid @RequestBody UserInfo info,HttpServletRequest request, HttpServletResponse response,BindingResult result) {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			//throw new RuntimeException(result.toString());
			return NinaJsonResult.erorMsg(result.toString());
		}
		boolean isExist = userService.queryUsernameIsExist(info.getUsername());
		String password = info.getPassword();
		String confirmedpassword = info.getConfirmPassword();
		if(!password.equals(confirmedpassword)) {
			return NinaJsonResult.erorMsg("两次密码输入不一致");
		}
		if(isExist) {
			return NinaJsonResult.erorMsg("用户名已经存在");
		}
		UserInfo resultData = userService.createUser(info);
		//设置浏览器端Cookies
	    CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(resultData),true);	    
	    //TODO:生成用户Token,存入redis会话
	    //同步购物车数据
	  	synchShopcartData(resultData.getId(), request, response);
		return NinaJsonResult.ok(resultData);
	}
	/**
	 * 注册登录成功后，同步购物车数据
	 */
	private void synchShopcartData(Long userId,HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 1.redis中无数据，          cookie中购物车为空，不做任何同步处理
		 *                      cookie中购物车不为空，直接放入redis中
		 * 2.Redis中有购物车数据,cookie中购物车为空，Redis购物车数据直接放入cookie
		 *                      cookie中购物车不为空，如果cookie中的某个商品在Redis中存在
		 *                      则以cookie为主，覆盖Redis中的数据
		 * 3.同步到Redis中以后，覆盖本地cookie中购物车数据
		 */
		
		//从Redis中获取购物车
		String shopcartJsonRedis = redisOperator.get(SHOPCART+":"+userId);
		
		//从Cookie中获取购物车
		String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART,true);
		
		if(StringUtils.isBlank(shopcartJsonRedis)) {
			//redis为空，cookie不为空，将cookie中的数据放入redis
			if(StringUtils.isNotBlank(shopcartStrCookie)) {
				redisOperator.set(SHOPCART+":"+userId, shopcartStrCookie);
			}
		}else {
			//Redis不为空
			if(StringUtils.isBlank(shopcartStrCookie)) {
				//Cookie为空，Redis购物车数据直接放入cookie
				CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis,true);
			}else {
				/**
				 *  cookie中购物车不为空，
				 *  如果cookie中的某个商品在Redis中存在则以cookie为主，
				 *  覆盖Redis中的数据
				 */
				/**
				 * 1.已经存在的，把cookie中对应的数量，，覆盖redis
				 * 2.存在的商品应标记为待删除，统一放入待删除list中
				 * 3.从cookie中清理所有待删除list
				 * 4.合并redis和cookie中的数据
				 * 5.更新redis和cookie
				 */
				List<ShopcartVO> shopCartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopcartVO.class);
				
				List<ShopcartVO> shopCartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopcartVO.class);
				
				//定义一个待删除list
				List<ShopcartVO> pendingDeleteList = new ArrayList<>();
				
				for(ShopcartVO redisShopcart:shopCartListRedis) {
					Long redisspecId = redisShopcart.getSpecId();
					for(ShopcartVO cookeShopcart:shopCartListCookie) {
						Long cookiespecId = cookeShopcart.getSpecId();
						//如果相同，用cookie中的数量覆盖Redis,不累加
						if(redisspecId.longValue() == cookiespecId.longValue()) {
							//如果相同，用cookie中的数量覆盖Redis,不累加
							redisShopcart.setBuyCounts(cookeShopcart.getBuyCounts());
							//将已经用于覆盖Redis的cookeShopcart放入待删除列表
							pendingDeleteList.add(cookeShopcart);
						}
					}
				}
				//从现有cookie中删除对应的覆盖过的商品
				shopCartListCookie.removeAll(pendingDeleteList);
				//最后将目前的list进行合并,更新后shopCartJsonRedis为最新的数据
				shopCartListRedis.addAll(shopCartListCookie);
				//将最新list更新到redis和cookie
				CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartJsonRedis),true);
				redisOperator.set(SHOPCART+":"+userId, JsonUtils.objectToJson(shopcartJsonRedis));
			}
		}
	}
}
