package com.xxu.type;

import java.sql.Timestamp;
public class Token {

	private int id;
	private String token;
	private Timestamp timeStamp;
	private static final long expireTime = 100000000;
	public Token() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public boolean isTokenExpired() {

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		long diff = currentTime.getTime() - timeStamp.getTime();
		System.out.println("the diff in time is"+diff);

	    return diff > expireTime;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", token=" + token + ", timeStamp="
				+ timeStamp + "]";
	}
	
}
