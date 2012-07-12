package me.guymer.spring.mybatis.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Widget {
	private int id;
	private String name;
	private Date createDate;
	private boolean active;
	
	public Date getCreateDate() {
		if (createDate == null) {
			return null;
		}
		
		return new Date(createDate.getTime());
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate == null ? null : new Date(createDate.getTime());
	}
}
