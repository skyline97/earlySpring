package com.skyline.earlySpring;

public class User {

	private String id;
	
	private String username;
	
	private Address address;
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public User() {
		System.out.println("lazy boy");
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", address="
				+ address + "]";
	}
	
	
	
}
