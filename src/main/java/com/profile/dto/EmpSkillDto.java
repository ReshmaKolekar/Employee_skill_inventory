package com.profile.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Emp_Skill")
public class EmpSkillDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "skillid")
	private int skillid;

	@Column(name = "name")
	private String skillName;

	@Column(name = "exp_in_year")
	private int experiance;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private EmployeeDto employee;

	public EmpSkillDto() {
		super();
	}

	public EmpSkillDto(int skillid, String skillName, int experiance) {
		super();
		this.skillid = skillid;
		this.skillName = skillName;
		this.experiance = experiance;
	}

	public String getSkillName() {
		return skillName;
	}

	public int getSkillid() {
		return skillid;
	}

	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public int getExperiance() {
		return experiance;
	}

	public void setExperiance(int experiance) {
		this.experiance = experiance;
	}

	public EmployeeDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDto employee) {
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
