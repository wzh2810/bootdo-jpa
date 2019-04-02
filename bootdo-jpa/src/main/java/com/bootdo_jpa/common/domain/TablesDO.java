package com.bootdo_jpa.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "information_schema.tables")
public class TablesDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "table_name")
	private String tableName;
	private String engine;
	@Column(name = "table_comment")
	private String tableComment;
	@Column(name = "create_time")
	private String createTime;
	
	public TablesDO() {
		super();
	}
	
	public TablesDO(String tableName, String engine, String tableComment, String createTime) {
		super();
		this.tableName = tableName;
		this.engine = engine;
		this.tableComment = tableComment;
		this.createTime = createTime;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
