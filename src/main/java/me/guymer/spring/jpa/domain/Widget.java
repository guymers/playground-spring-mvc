package me.guymer.spring.jpa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "widget")
public class Widget implements Serializable {
	
	private static final long serialVersionUID = -3739549762565393073L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "widget_id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "active")
	private boolean active;
}
