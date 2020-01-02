package org.nina.repository.support;
import org.springframework.core.convert.converter.Converter;
/**
 * 
 * @author riverplant
 *
 * @param <T>
 * @param <I>
 */
public interface Domain2InfoConverter<T, I> extends Converter<T, I>{

}
