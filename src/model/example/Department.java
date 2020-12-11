package model.example;

import model.base.Table;

@Table(name="tb_Department")
public class Department {
	@Table(column="id")
	String id;
	@Table(column="name")
	String name;
	
	public Department() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
