package com.profile.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class EmployeeDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "contact_no")
	private long contact;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	// @OneToOne(cascade=CascadeType.ALL)
	@OneToMany(mappedBy = "employee")
	// @ManyToMany(cascade = CascadeType.ALL)

	private List<EmpSkillDto> skill;

	public EmployeeDto() {
		// TODO Auto-generated constructor stub
	}

	public EmployeeDto(String firstName, String lastName, long contact, String email, String password) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.contact = contact;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
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

	public List<EmpSkillDto> getSkill() {
		return skill;
	}

	public void setSkill(List<EmpSkillDto> skill) {
		this.skill = skill;
	}

}
