package com.mulcam.demo.crawling;

public class FireStation {
	private String name;
	private String addr;
	private String tel;
//	private double lng;	//나중에 위도 경도 작업 가능
//	private double lat;
	
	public FireStation() {}
	public FireStation(String name, String addr, String tel) {
		super();
		this.name = name;
		this.addr = addr;
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "FireStation [name=" + name + ", addr=" + addr + ", tel=" + tel + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

}
