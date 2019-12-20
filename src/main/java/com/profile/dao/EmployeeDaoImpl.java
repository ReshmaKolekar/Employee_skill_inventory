package com.profile.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profile.Repository.EmployeeReo;
import com.profile.dto.EmployeeDto;

@Service
public class EmployeeDaoImpl implements IEmployeeDao {

	@Autowired
	EmployeeReo employeeReo;

	public String save(EmployeeDto emp) {
		try {
			employeeReo.save(emp);
			return "success";
		} catch (Exception e) {
			return "failure";
		}

	}

	public List<EmployeeDto> findAll() {
		return employeeReo.findAll();

	}

	public EmployeeDto delete(EmployeeDto emp) {
		employeeReo.delete(emp);
		return emp;

	}

	@Override
	public EmployeeDto getEmpSkillsByEmpId(int id) {
		// TODO Auto-generated method stub
		return employeeReo.findById(id);
	}
}
