package me.guymer.spring.mybatis.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Widget {
	private int id;
	private String name;
	private Date createDate;
	private boolean active;
}
