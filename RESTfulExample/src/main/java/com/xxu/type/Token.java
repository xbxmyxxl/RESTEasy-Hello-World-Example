package com.xxu.type;

import java.sql.Timestamp;

public class Token {

	private int id;
	private String token;
	private Timestamp timeStamp;
	private static final long expireTime = 1000;/* one second */

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

	/**
	 * @return boolean indicate it is expired or not
	 */
	public boolean isTokenExpired() {
		/*
		 * calculate the difference of the time stamp with the current time, and
		 * compare to the expireTime constant to decide whether it is expired or
		 * not. Change the expireTime, if we want a longer expire time,
		 * currently it is set to 1 second, for testing purposes.
		 */
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		long diff = currentTime.getTime() - timeStamp.getTime();
		System.out.println("the diff in time is" + diff);

		return diff > expireTime;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", token=" + token + ", timeStamp="
				+ timeStamp + "]";
	}

}
