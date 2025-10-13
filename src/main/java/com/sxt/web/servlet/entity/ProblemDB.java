package com.sxt.web.servlet.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProblemDB {
	private Integer pid;
	private Integer uid;//反馈人ID
	private String account;//账号
	private String title;//标题
	private String page;//页面
	private String content;//内容
	private String link;//联系方式
	private Integer status;//0未解决 1解决
	private LocalDateTime time;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public String getFormattedBeginTime() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    return time.format(formatter);
	  }

	@Override
	public String toString() {
		return "ProblemDB [pid=" + pid + ", uid=" + uid + ", account=" + account + ", title=" + title + ", page=" + page
				+ ", content=" + content + ", link=" + link + ", status=" + status + ", time=" + time + "]";
	}
	
	
	

}
