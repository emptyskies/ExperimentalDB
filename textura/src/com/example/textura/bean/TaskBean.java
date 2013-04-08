package com.example.textura.bean;

import java.io.Serializable;

import android.content.ContentValues;

/**
 * Class for Task data encapsulation
 * 
 * 
 */
public class TaskBean implements Serializable {
	/**
	 * Serialize the version id
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;
	private String detail;

	
	private int classificationId;
	private String classificationName;
	private int classificationColor;

	private int year;
	private int mouth;
	private int day;

	private int hour;
	private int minute;
	
	private int priority = 0;
	
	private int state = 0;

	/**
	 * Transfer the TaskBean to ContentValues, to insert or update database value
	 * 
	 * @return
	 */
	public ContentValues change2ContentValues() {
		ContentValues cv = new ContentValues();
		cv.put("taskId", id);
		cv.put("name", name);
		cv.put("detail", detail);
		cv.put("classificationName", classificationName);
		cv.put("classificationId", classificationId);
		cv.put("classificationColor", classificationColor);
		cv.put("year", year);
		cv.put("mouth", mouth);
		cv.put("day", day);
		cv.put("hour", hour);
		cv.put("minute", minute);
		cv.put("priority", priority);
		cv.put("state", state);
		return cv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public int getClassificationColor() {
		return classificationColor;
	}

	public void setClassificationColor(int classificationColor) {
		this.classificationColor = classificationColor;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMouth() {
		return mouth;
	}

	public void setMouth(int mouth) {
		this.mouth = mouth;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
