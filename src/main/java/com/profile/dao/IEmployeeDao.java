package com.profile.dao;

import com.profile.dto.EmployeeDto;

public interface IEmployeeDao {

	public String save(EmployeeDto emp);

	public EmployeeDto getEmpSkillsByEmpId(int id);

}
