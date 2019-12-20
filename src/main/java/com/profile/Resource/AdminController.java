package com.profile.Resource;

import java.util.List;

//import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.dto.EmployeeDto;
import com.profile.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/admin")
@Api(value = "Admin Resource ", description = "Admin functions")
public class AdminController {

	@Autowired
	AdminService adminService;

	@ApiOperation(value = "Returns all employees with specified skill ")
	@GetMapping("/basicSearch/{skillname}")
	public List<EmployeeDto> getAllEmp(@PathVariable(value = "skillname") String skillname) {
		return adminService.getAllEmpBySkillName(skillname);

	}
}
