package org.nina.commons.utils;

import java.util.List;

/**
 * 返回分页Grid的数据格式
 * @author riverplant
 *
 */
public class PageGidResult {
private int page;      //当前页数
private int total;     //总页数
private long record;   //总记录数
private List<?> rows;  //每页显示的内容
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public long getRecord() {
	return record;
}
public void setRecord(long record) {
	this.record = record;
}
public List<?> getRows() {
	return rows;
}
public void setRows(List<?> rows) {
	this.rows = rows;
}
}
