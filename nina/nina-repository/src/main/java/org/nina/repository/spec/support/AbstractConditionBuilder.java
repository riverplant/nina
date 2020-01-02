/**
 * 
 */
package org.nina.repository.spec.support;

import java.util.Collection;
import java.util.Date;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * @author riverplant
 * @param <T>
 *
 */
public abstract class AbstractConditionBuilder<T> {
	/**
	 * 获得封装对象指定属性的值
	 * 
	 * @param root:封装的对象
	 * @param property
	 * @return
	 */
	protected Path getPath(Root root, String property) {

		String[] names = StringUtils.split(property, ".");
		// 获取该封装对象所有属性的值
		Path path = root.get(names[0]);
		for (int i = 1; i < names.length; i++) {
			path = root.get(names[i]);
		}
		return path;
	}

	/**
	 * 判断是否需要添加where条件
	 * 
	 * @param value
	 * @return
	 */
	protected boolean needAddCondition(Object value) {
		boolean addCondition = false;
		if (value != null) {
			if (value instanceof String) {
				if (StringUtils.isNotBlank(value.toString())) {
					addCondition = true;
				}
			} else if (value.getClass().isArray()) {
				if (ArrayUtils.isNotEmpty((Object[]) value)) {
					addCondition = true;
				}
			} else if (value instanceof Collection) {
				if (CollectionUtils.isNotEmpty((Collection) value)) {
					addCondition = true;
				}
			} else {
				addCondition = true;
			}
		}
		return addCondition;
	}

	/**
	 * 当范围查询的条件是小于，并且值的类型是Date时，将传入的Date值变为当天的夜里12点的值。
	 * 
	 * @param maxValue
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Comparable processMaxValueOnDate(Comparable maxValue) {
		if (maxValue instanceof Date) {
			maxValue = new DateTime(maxValue).withTimeAtStartOfDay().plusDays(1).plusSeconds(-1).toDate();
		}
		return maxValue;
	}

	/**
	 * 添加in条件
	 *
	 * @param queryWraper
	 * @param values
	 */
	protected void addInConditionToColumn(QueryWraper<T> queryWraper, String column, Object values) {
		if (needAddCondition(values)) {
			Path<?> fieldPath = getPath(queryWraper.getRoot(), column);
			if (values.getClass().isArray()) {
				queryWraper.addPredicate(fieldPath.in((Object[]) values));
			} else if (values instanceof Collection) {
				queryWraper.addPredicate(fieldPath.in((Collection<?>) values));
			}
		}
	}

	/**
	 * 添加between条件查询
	 * 
	 * @param queryWraper
	 * @param experssion
	 * @param minValue
	 *            范围下限
	 * @param maxValue
	 *            范围上限
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addBetweenConditionToColumn(QueryWraper<T> queryWraper, String column, Comparable minValue,
			Comparable maxValue) {
		if (minValue != null || maxValue != null) {
			Path<? extends Comparable> fieldPath = getPath(queryWraper.getRoot(), column);
			if (minValue != null && maxValue != null) {
				queryWraper.addPredicate(
						queryWraper.getCb().between(fieldPath, minValue, processMaxValueOnDate(maxValue)));
			} else if (minValue != null) {
				queryWraper.addPredicate(queryWraper.getCb().greaterThanOrEqualTo(fieldPath, minValue));
			} else if (maxValue != null) {
				queryWraper.addPredicate(
						queryWraper.getCb().lessThanOrEqualTo(fieldPath, processMaxValueOnDate(maxValue)));
			}
		}
	}

	/**
	 * 添加大于条件查询
	 * 
	 * @param queryWraper
	 * @param experssion
	 * @param minValue
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addGreaterThanConditionToColumn(QueryWraper<T> queryWraper, String column, Comparable minValue) {
		if (minValue != null) {
			Path<? extends Comparable> fieldPath = getPath(queryWraper.getRoot(), column);
			queryWraper.addPredicate(queryWraper.getCb().greaterThan(fieldPath, minValue));
		}
	}

	/**
	 * 添加小于条件查询
	 * 
	 * @param queryWraper
	 * @param experssion
	 * @param minValue
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addLessThanConditionToColumn(QueryWraper<T> queryWraper, String column, Comparable minValue) {
		if (minValue != null) {
			Path<? extends Comparable> fieldPath = getPath(queryWraper.getRoot(), column);
			queryWraper.addPredicate(queryWraper.getCb().lessThan(fieldPath, minValue));
		}
	}

	/**
	 * 添加大于等于条件查询
	 * 
	 * @param queryWraper
	 * @param experssion
	 * @param minValue
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addGreaterThanOrEqualConditionToColumn(QueryWraper<T> queryWraper, String column,
			Comparable minValue) {
		if (minValue != null) {
			Path<? extends Comparable> fieldPath = getPath(queryWraper.getRoot(), column);
			queryWraper.addPredicate(queryWraper.getCb().greaterThanOrEqualTo(fieldPath, minValue));
		}
	}

	/**
	 * 添加小于等于条件查询
	 * 
	 * @param queryWraper
	 * @param experssion
	 * @param minValue
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addLessThanOrEqualConditionToColumn(QueryWraper<T> queryWraper, String column, Comparable minValue) {
		if (minValue != null) {
			Path<? extends Comparable> fieldPath = getPath(queryWraper.getRoot(), column);
			queryWraper.addPredicate(queryWraper.getCb().lessThanOrEqualTo(fieldPath, minValue));
		}
	}

	/**
	 * <pre>
	 * 添加like条件
	 * 
	 * <pre>
	 * 
	 * @param queryWraper
	 * @param column
	 * @param value
	 */
	protected void addLikeConditionToColumn(QueryWraper<T> queryWraper, String column, String value) {
		if (StringUtils.isNotBlank(value)) {
			queryWraper.addPredicate(createLikeCondition(queryWraper, column, value));
		}
	}

	protected Predicate createLikeCondition(QueryWraper<T> queryWraper, String column, String value) {
		Path<String> path = getPath(queryWraper.getRoot(), column);
		Predicate predicate = queryWraper.getCb().like(path, "%" + value + "%");
		return predicate;
	}
	/**
	 * <pre>
	 * 添加like条件
	 * 
	 * <pre>
	 * 
	 * @param queryWraper
	 * @param column
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	protected void addStartsWidthConditionToColumn(QueryWraper<T> queryWraper, String column, String value) {
		if (StringUtils.isNotBlank(value)) {
			Path<String> fieldPath = getPath(queryWraper.getRoot(), column);
			queryWraper.addPredicate(queryWraper.getCb().like(fieldPath, value + "%"));
		}
	}
	/**
	 * 添加等于条件
	 * @param queryWraper
	 * @param column
	 * @param value
	 */
	protected void addEqualsConditionToColumn(QueryWraper<T> queryWraper, String column, Object value) {
		Path fieldPath = getPath(queryWraper.getRoot(), column);
		queryWraper.addPredicate(queryWraper.getCb().equal(fieldPath,value ));
	}
	
	/**
	 * 添加不等于条件
	 * @param queryWraper
	 * @param column
	 * @param value
	 */
	protected void addnotEqualsConditionToColumn(QueryWraper<T> queryWraper, String column, Object value) {
		Path fieldPath = getPath(queryWraper.getRoot(), column);
		queryWraper.addPredicate(queryWraper.getCb().notEqual(fieldPath,value ));
	}
	
	
}
