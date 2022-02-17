package com.xgk.demo.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Student implements Serializable {

	// 学号
	private Integer id;
	// 姓名
	private String name;
	// 年龄
	private Integer age;
	// 班级
	private String className;

}
