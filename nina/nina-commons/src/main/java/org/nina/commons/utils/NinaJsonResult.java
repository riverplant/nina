package org.nina.commons.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author jli 封装调取接口返回内容
 * @Description: Json Object 200:成功 500:错误,错误信息在msg字段中 501:Bean验证错误,错误以map形式返回
 *               502:拦截器拦截到Token错误 555:异常抛出信息 556:第三方校验异常
 */
public class NinaJsonResult {
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	@JsonIgnore
	private String ok;

	public static NinaJsonResult build(Integer status, String msg, Object data) {
		return new NinaJsonResult(status, msg, data);
	}

	public NinaJsonResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public NinaJsonResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public NinaJsonResult() {

	}

	public Boolean isOK() {
		return this.status == 200;
	}

	public static NinaJsonResult ok(Object data) {
		return new NinaJsonResult(data);
	}

	public static NinaJsonResult ok() {
		return new NinaJsonResult(null);
	}

	public static NinaJsonResult erorMsg(String msg) {
		return new NinaJsonResult(500, msg, null);
	}

	public static NinaJsonResult erorMap(Object data) {
		return new NinaJsonResult(501, "error", data);
	}

	public static NinaJsonResult erorTokenMsg(String msg) {
		return new NinaJsonResult(502, msg, null);
	}

	public static NinaJsonResult erorException(String msg) {
		return new NinaJsonResult(555, msg, null);
	}

	public static NinaJsonResult errorUserQQ(String msg) {
		return new NinaJsonResult(556, msg, null);
	}

}
