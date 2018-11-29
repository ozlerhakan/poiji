package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellRange;

public class Classes {

	@ExcelCellRange(begin = 0, end = 4)
	private PersonTest classA;
	@ExcelCellRange(begin = 5, end = 9)
	private PersonTest classB;

	public PersonTest getClassA() {
		return classA;
	}

	public void setClassA(PersonTest classA) {
		this.classA = classA;
	}

	public PersonTest getClassB() {
		return classB;
	}

	public void setClassB(PersonTest classB) {
		this.classB = classB;
	}

}
