package org.nina.repository.support;

import org.apache.commons.beanutils.BeanUtils;

public abstract class AbstractDomain2InfoConverter<T, I> implements Domain2InfoConverter<T, I> {

	@Override
	public I convert(T domain) {
		I info = null;
		try {
			@SuppressWarnings("unchecked")
			Class<I> clazz = GenericUtils.getGenericClass(getClass(), 1);
			info = clazz.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(domain, info);
			doConvert(domain, info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	protected abstract void doConvert(T domain, I info) throws Exception;

}
