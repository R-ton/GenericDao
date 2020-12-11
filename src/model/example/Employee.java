package model.example;

import java.util.List;

import model.base.IgnoreColumn;
import model.base.Table;
//ímport 
@Table(name="tb_Employee")
public class Employee {
	
	@Table(column="name")
	String nameemployee;
	@Table(column="id")
	String idemp;
	@Table(column="salary")
	double salaryemployee;
	
	@Table(list="tb_Department") //tsy maintsy nom de table
	//@IgnoreColumn()
	List<Department> deptemployee;
	//@Table(column="dep")
	String dep;
	
	@IgnoreColumn()
	String remark;
	
	public Employee() {
		super();
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getIdemp() {
		return idemp;
	}

	public void setIdemp(String idemp) {
		this.idemp = idemp;
	}

	public String getNameemployee() {
		return nameemployee;
	}

	public void setNameemployee(String nameemployee) {
		this.nameemployee = nameemployee;
	}

	public double getSalaryemployee() {
		return salaryemployee;
	}

	public void setSalaryemployee(double salaryemployee) {
		this.salaryemployee = salaryemployee;
	}

	public List<Department> getDeptemployee() {
		return deptemployee;
	}
	public void setDeptemployee(List<Department> deptemployee) {
		this.deptemployee = deptemployee;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String depemployee) {
		this.dep = depemployee;
	}
}
