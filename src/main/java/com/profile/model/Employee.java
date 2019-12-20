package com.profile.model;

public class Employee {

	long contact;
	String first_name, last_name, email, password;

	public Employee() {

	}

	public Employee(long contact, String first_name, String last_name, String email, String password) {
		super();

		this.contact = contact;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		// this.skill = skill;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

}
