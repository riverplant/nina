package org.nina.security.Oauth2;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
/**
 * 参考FixedAuthoritiesExtractor
 * @author riverplant
 * 通过数据库来获取授权
 */
public class NinaAuthoritiesExtractor implements AuthoritiesExtractor {

	@Override
	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
