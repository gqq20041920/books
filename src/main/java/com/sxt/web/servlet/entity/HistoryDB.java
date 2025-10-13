package com.sxt.web.servlet.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
//图书借阅历史记录
public class HistoryDB {
	private Integer hid;//记录表ID
	private Integer uid;//用户ID
	private String name;//用户姓名
	private String account;//用户账号
	private Integer bid;//图书ID
	private String bookName;//图书名称
	private LocalDateTime beginTime;//借书时间
	private LocalDateTime endTime;//还书时间
	private Integer status;//借阅状态
	public Integer getHid() {
		return hid;
	}
	public void setHid(Integer hid) {
		this.hid = hid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public LocalDateTime getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(LocalDateTime beginTime) {
		this.beginTime = beginTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	// 添加格式化方法
	  public String getFormattedBeginTime() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    return beginTime.format(formatter);
	  }

	  public String getFormattedEndTime() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    return endTime.format(formatter);
	  }
	@Override
	public String toString() {
		return "HistoryDB [hid=" + hid + ", uid=" + uid + ", name=" + name + ", account=" + account + ", bid=" + bid
				+ ", bookName=" + bookName + ", beginTime=" + beginTime + ", endTime=" + endTime + ", status=" + status
				+ "]";
	}
	

}
