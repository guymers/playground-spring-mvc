package me.guymer.spring.web.ext;

import lombok.Data;

@Data
public class ExtJson {

	private Boolean success;
	private String message;
	private Object data;
	private int total;

}
