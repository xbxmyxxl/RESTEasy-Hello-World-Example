package com.xxu.type;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class Item {
	private int id;
	private String name;
	private String brand;
	private float price;
	private float reduced_price;
	private int quantity;

	// Getters and setters
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@XmlAttribute
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item() {
		this.reduced_price = 0;
		this.quantity = 1;
	}

	public void SetAttributes(int id, String name, String brand, float price) {
		this.name = name;
		this.id = id;
		this.brand = brand;
		this.price = price;
	}

	public void print() {
		System.out.println(this.id + this.name + this.brand + this.price);
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", brand=" + brand
				+ ", price=" + price + ", reduced_price=" + reduced_price
				+ ", quantity=" + quantity + "]";
	}
}
