package com.alibaba.easyexcel.test.core.localdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ywzou
 */
public class DataEntry {
	private String userName;
	private int age;
	private boolean hashChild;
	private BigDecimal income;
	private double height;
	private Double weight;

//	@DateTimeFormat(value = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime birthdayTime;// yyyy-MM-dd HH:mm:ss

	@JSONField(format = "yyyy-MM-dd")
	private LocalDate birthday;// yyyy-MM-dd

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;// yyyy-MM-dd HH:mm:ss

//	@DateTimeFormat(value = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;// yyyy-MM-dd HH:mm:ss

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isHashChild() {
		return hashChild;
	}

	public void setHashChild(boolean hashChild) {
		this.hashChild = hashChild;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public LocalDateTime getBirthdayTime() {
		return birthdayTime;
	}

	public void setBirthdayTime(LocalDateTime birthdayTime) {
		this.birthdayTime = birthdayTime;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
