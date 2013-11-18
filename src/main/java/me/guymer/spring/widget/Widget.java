package me.guymer.spring.widget;

import java.io.Serializable;
import java.util.Date;

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
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "active")
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
