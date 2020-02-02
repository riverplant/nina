package org.nina.security.Oauth2;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
/**
 * 可以参考FixedPrincipalExtractor
 * @author riverplant
 *
 */
public class NinaPrincipalExtractor implements PrincipalExtractor {

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
