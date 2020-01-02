package org.nina.repository.spec;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.nina.domain.User;
import org.nina.dto.UserCondition;
import org.nina.repository.spec.support.NinaSimpleSpecification;
import org.nina.repository.spec.support.QueryWraper;
/**
 * 
 * @author riverplant
 *
 */
public class UserSpec extends NinaSimpleSpecification<User, UserCondition> {
	
	private static final long serialVersionUID = 1L;

	public UserSpec(UserCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<User> queryWraper) {
		/**
		 * 按照姓名(like)
		 */
		//addLikeCondition(queryWraper, "username");
		/**
		 * 创建or的查询
		 */
		if(StringUtils.isNotBlank(getCondition().getUsername())) {
			Predicate nameLike = createLikeCondition(queryWraper, "username", getCondition().getUsername());
			Predicate emailLike = createLikeCondition(queryWraper, "email", getCondition().getEmail());
			queryWraper.addPredicate(queryWraper.getCb().or(nameLike,emailLike));
		}
		/**
		 * 按照年龄
		 */
		addBetweenCondition(queryWraper, "age");
		
		/**
		 *按照性别来查询
		 */
		addEqualsCondition(queryWraper, "sex");
		/**
		 * 为查询结果设置特殊条件，该条件非前段用户设置。
		 */
		addEqualsConditionToColumn(queryWraper, "nickname", null);
	}

	@Override
	protected void addFetch(Root<User> root) {
		/**
		 * 设置查询时相关联对象的关联查询
		 */
		//root.fetch("",JoinType.LEFT);
		super.addFetch(root);
	}
	
	

}
