package me.guymer.spring.widget;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.ibatis.type.Alias;

@Data
@Entity
@Table(name = "widget")
@Alias("Widget")
public class Widget implements Serializable {

	private static final long serialVersionUID = -3739549762565393073L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "widget_id")
	private int id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "create_date")
	private LocalDate createDate;

	@Column(name = "active")
	private boolean active;
}
