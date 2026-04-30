package com.example.employee_analyzer_v1.model;

public class Employee {
private int Id;
private String firstName;
private String lastName;
private double salary;
private Integer managerId;
public Employee() {
	super();
	Id = Id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.salary = salary;
	this.managerId = managerId;
}
public int getId() {
	return Id;
}
public void setId(int id) {
	Id = id;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public double getSalary() {
	return salary;
}
public void setSalary(double salary) {
	this.salary = salary;
}
public Integer getManagerId() {
	return managerId;
}
public void setManagerId(Integer managerId) {
	this.managerId = managerId;
}


}
