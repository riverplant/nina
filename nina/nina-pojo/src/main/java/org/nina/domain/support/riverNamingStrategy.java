package org.nina.domain.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;
/**
 * 命名类
 * @author riverplant
 *
 */
public class riverNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
	private static final long serialVersionUID = 1L;

	@Override
	protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
		//自定义数据库表名和字段名
		return super.toIdentifier("river_"+stringForm, buildingContext);
	}

}
