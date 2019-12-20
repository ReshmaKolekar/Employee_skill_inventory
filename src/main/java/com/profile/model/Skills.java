package com.profile.model;

public class Skills {

	private int skillid, experiance;
	private String skillName;

	public Skills() {
		super();
	}

	public Skills(int skillid, String skillName, int experiance) {
		super();
		this.skillid = skillid;
		this.skillName = skillName;
	}

	public int getSkillid() {
		return skillid;
	}

	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}

	public String getSkillName() {
		return skillName;
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

	/*
	 * public Skills(int id, String skillName) { super(); this.id = id;
	 * this.skillName = skillName; }
	 */

}
