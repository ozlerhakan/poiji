package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellRange;

public class Classes {

	@ExcelCellRange
	private PersonATest classA;
	@ExcelCellRange
	private PersonBTest classB;

	public PersonATest getClassA() {
		return classA;
	}

	public void setClassA(PersonATest classA) {
		this.classA = classA;
	}

	public PersonBTest getClassB() {
		return classB;
	}

	public void setClassB(PersonBTest classB) {
		this.classB = classB;
	}

}
