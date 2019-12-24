package com.profile.Resource;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.CustomException.RecordNotFoundException;
import com.profile.Repository.EmpSkillRepo;
import com.profile.Repository.EmployeeReo;
import com.profile.Repository.SkillsRepo;
import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.model.Employee;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.*;

@RestController
@RequestMapping("/rest/Employee")
@Api(value = "Employee Resource ", description = "Employee data Management")
public class EmployeeController {

	// private static final Logger logger =
	// LogManager.getLogger(EmployeeController.class);

	@Autowired
	EmployeeReo employeeReo;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	SkillsRepo skillsRepo;

	@Autowired
	EmpSkillRepo empSkillRepo;

	// Register and Login API's

	@ApiOperation(value = "Register the user ")
	@PostMapping("/createemployee")
	public @Valid EmployeeDto createUser(@Valid @RequestBody EmployeeDto employee, HttpServletRequest req) {
		//return employeeService.save(employee);
		return employeeReo.save(employee);
		
	}

	@ApiOperation(value = "Authenticate the user ")
	@PostMapping("/login")
	public String authenticate(@RequestBody Employee employee, HttpServletRequest req) throws IOException {

		// logger.info("request URI :" + req.getRequestURL() + "req headers :" +
		// req.getMethod());

		// ObjectMapper om = new ObjectMapper();

		// Employee employeeBody = om.readValue(req.getReader(), Employee.class);

		return employeeService.authenticate(employee);

	}

	@ApiOperation(value = "Returns List of employees with EmployeeData ")
	@GetMapping("/EmployeeList")
	public List<EmployeeDto> getAllEmp() {
		return employeeService.getAllEmp();

	}

	@ApiOperation(value = "Save user skills ")
	@PostMapping("/addEmpskill/{id}")
	public ResponseEntity<EmployeeDto> addEmpskill(@PathVariable(value = "id") int id,
			@RequestBody SkillList skillList) {

		EmployeeDto employee = employeeService.addEmpskill(id, skillList);
		return ResponseEntity.ok().body(employee);

	}

	@ApiOperation(value = "Delete Employee data ")
	@DeleteMapping("/delete/emp/{id}")
	public ResponseEntity<String> deleteEmplSkills(@PathVariable(value = "id") int id) {

		String response = employeeService.deleteEmp(id);
		if (response.equalsIgnoreCase("success"))
			return ResponseEntity.ok().body(response);

		return ResponseEntity.notFound().build();

	}
	// Employee Skill API's

	@ApiOperation(value = "Returns employee data by id ")
	@GetMapping("/getEmp/{id}")
	public ResponseEntity<?> getEmpById(@PathVariable(value = "id") int id) throws RecordNotFoundException {

		EmployeeDto employeeDto = employeeService.getEmpById(id);
		try {
			if (employeeDto == null)
				throw new RecordNotFoundException("Record not found with employee_id " + id);
		} catch (RecordNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.ok().body(employeeDto);
	}

	// update single skill
	@ApiOperation(value = "Update one skill ")
	@PutMapping("/update/empskill/{id}")
	public ResponseEntity<String> updateEmpSkill(@PathVariable(value = "id") int id, @RequestParam String skill_name,
			@RequestBody Skills skill) {

		String response = employeeService.updateEmpSkill(id, skill_name, skill);
		if (response.equalsIgnoreCase("success")) {
			return ResponseEntity.ok().body("skill updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failure while updating skill");
		}

	}

	// update multiple Skills
	@ApiOperation(value = "Update multiple skills ")
	@PutMapping("/update/empskills/{id}")
	public ResponseEntity<EmployeeDto> updateEmpSkills(@PathVariable(value = "id") int id,
			@RequestBody SkillList skillList) {

		EmployeeDto response = employeeService.updateEmpSkillSet(id, skillList);
		return ResponseEntity.ok().body(response);
	}

	@ApiOperation(value = "Delete Employee Skill mapping data ")
	@DeleteMapping("/delete/empSkill/{empid}/{skillid}")
	public ResponseEntity<?> deleteEmpSkillData(@PathVariable(value = "empid") int empid,
			@PathVariable(value = "skillid") int skillid) {

		EmpSkillDto response = employeeService.deleteEmpSkillData(empid, skillid);
		try {
			if (response == null)
				throw new RecordNotFoundException("Record not found with employee_id " + empid);
		} catch (RecordNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.ok()
				.body("Mapping for employee: " + empid + " with Skillid : " + skillid + " is deleted");
	}

}
