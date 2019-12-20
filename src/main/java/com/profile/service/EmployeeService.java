package com.profile.service;

import java.util.List;

import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.model.Employee;
import com.profile.model.SkillList;
import com.profile.model.Skills;

public interface EmployeeService {

	EmployeeDto getEmpByID(int id);

	String save(Employee employee);

	String sendMail(String to, String response);

	List<EmpSkillDto> addEmpskill(int id, SkillList skillList);

	List<EmployeeDto> getAllEmp();

	EmployeeDto getEmpById(int id);

	String deleteEmp(int id);

	String updateEmpSkill(int id, String skill_name, Skills skill);

	String authenticate(Employee employee);

	String updateEmpSkillSet(int id, SkillList skillList);

	String deleteEmpSkillData(int id);

}
