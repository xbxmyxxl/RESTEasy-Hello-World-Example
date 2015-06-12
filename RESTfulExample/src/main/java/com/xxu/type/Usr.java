package com.xxu.type;

public class Usr {
	private int id;
	private String name;
	private String publicKey;
	private String randomString;
	
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
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getRandomString() {
		return randomString;
	}
	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}
	@Override
	public String toString() {
		return "Usr [id=" + id + ", name=" + name + ", publicKey=" + publicKey
				+ ", randomString=" + randomString + "]";
	}

}
