
package org.nina.repository.spec.support;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author riverplant
 *
 */
public class QueryWraper<T> {

	private Root<T> root;
	private CriteriaQuery<?> query;
	private CriteriaBuilder cb;
	/**
	 * JPA Predicate 集合
	 */
	private List<Predicate> predicates;
	/**
	 * @param root
	 *            JPA Root
	 * @param cb
	 *            JPA CriteriaBuilder
	 * @param predicates
	 *            JPA Predicate 集合
	 */
	public QueryWraper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates) {
		this.root = root;
		this.query = query;
		this.cb = cb;
		this.predicates = predicates;
	}
	public Root<T> getRoot() {
		return root;
	}
	public void setRoot(Root<T> root) {
		this.root = root;
	}
	public CriteriaQuery<?> getQuery() {
		return query;
	}
	public void setQuery(CriteriaQuery<?> query) {
		this.query = query;
	}
	public CriteriaBuilder getCb() {
		return cb;
	}
	public void setCb(CriteriaBuilder cb) {
		this.cb = cb;
	}
	public List<Predicate> getPredicates() {
		return predicates;
	}
	public void setPredicates(List<Predicate> predicates) {
		this.predicates = predicates;
	}
	public void addPredicate(Predicate predicate) {
		this.predicates.add(predicate);
	}
}
