package org.nina.api.support;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.server.MethodNotAllowedException;

/**
 * 
 * @author riverplant
 *
 */
@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleFileNotFoundException(RuntimeException e) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "Fail");
		result.put("code", HttpStatus.NOT_FOUND);
		result.put("errMsg", e.getMessage());

		return result;
	}
	
	@ExceptionHandler(Forbidden.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public Map<String, Object> handleForbidden(RuntimeException e) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "Fail");
		result.put("code", HttpStatus.FORBIDDEN);
		result.put("errMsg", e.getMessage());

		return result;
	}
	
	@ExceptionHandler(MethodNotAllowedException.class)
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public Map<String, Object> handleMethodNotAllowedException(RuntimeException e) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "Fail");
		result.put("code", HttpStatus.METHOD_NOT_ALLOWED);
		result.put("errMsg", e.getMessage());

		return result;
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleRuntimeExceptio(RuntimeException e) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "Fail");
		result.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
		result.put("errMsg", e.getMessage());

		return result;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleExceptio(RuntimeException e) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "Fail");
		result.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
		result.put("errMsg", e.getMessage());

		return result;
	}
	
	
}
