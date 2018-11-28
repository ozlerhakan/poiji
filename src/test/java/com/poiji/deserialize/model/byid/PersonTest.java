package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;

public class PersonTest {

	@ExcelCellName("Name")
	private String name;
	@ExcelCellName("Age")
	private Integer age;
	@ExcelCellName("City")
	private String city;
	@ExcelCellName("State")
	private String state;
	@ExcelCellName("Zip Code")
	private Integer zip;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

}
