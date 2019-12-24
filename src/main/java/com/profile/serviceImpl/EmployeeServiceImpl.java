package com.profile.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.profile.Repository.EmpSkillRepo;
import com.profile.Repository.EmployeeReo;
import com.profile.Repository.SkillsRepo;
import com.profile.dao.IEmpSkillDao;
import com.profile.dao.IEmployeeDao;
import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.dto.SkillDto;
import com.profile.model.Employee;
import com.profile.model.Mail;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	IEmployeeDao iEmployeeDao;

	@Autowired
	IEmpSkillDao iEmpSkillDao;

	@Autowired
	EmployeeReo employeeReo;

	@Autowired
	SkillsRepo skillsRepo;

	@Autowired
	EmpSkillRepo empSkillRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EmployeeDto getEmpByID(int id) {

		EmployeeDto employee = null;
		// employee = employeeDao.findById(id);

		if (employee == null)
			throw new NullPointerException("User Not found");

		return employee;

	}

	@Override
	public String save(Employee emp) {

		EmployeeDto employee = new EmployeeDto();
		employee.setFirstName(emp.getFirst_name());
		employee.setLastName(emp.getLast_name());
		employee.setContact(emp.getContact());
		employee.setEmail(emp.getEmail());

		employee.setPassword(encodePass(emp.getPassword()));

		// if ((employeeReo.findByEmailAndPassword(emp.getEmail(), emp.getPassword()) ==
		// null)) {

		EmployeeDto employeeFromDB = employeeReo.findByEmail(emp.getEmail());
		if (employeeFromDB == null) {
			String response = iEmployeeDao.save(employee);

			 //return "User registered successfully and " + sendMail(emp.getEmail(),response) + " to the user";
			return "success";
			
		}

		else {
			return "User already exist";
		}
	}

	public String encodePass(String plainPassword) {

		String encryptedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
		System.out.println(encryptedPassword);

		boolean matched = BCrypt.checkpw(plainPassword, encryptedPassword);
		System.out.println(matched);

		return encryptedPassword;
	}

	public String sendMail(String to, String response) {

		String URI = "http://localhost:8085/Mail/sendmail";
		// String URI = "http://Email_Service/Mail/sendmail";
		String subject = "User Account Creation";
		String content;
		if (response.equalsIgnoreCase("success")) {

			Mail sendMail = new Mail(to, subject, "User account created Successfully");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Mail> request = new HttpEntity<>(sendMail, headers);
			return restTemplate.postForObject(URI, request, String.class);
		}

		else {
			Mail sendMail = new Mail(to, subject, "User account creation failed");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Mail> request = new HttpEntity<>(sendMail, headers);
			return restTemplate.postForObject(URI, request, String.class);
		}
	}

	@Override
	public String authenticate(Employee employee) {
		EmployeeDto employeeFromDb = employeeReo.findByEmail(employee.getEmail());
		if (employeeFromDb != null) {
			// if (employee.getPassword().equals(employeeFromDb.getPassword()))
			if (BCrypt.checkpw(employee.getPassword(), employeeFromDb.getPassword()))
				return "User is authenticated Successfully !!! ";
			else
				return " incorrect password ";

		} else {
			return "You have entered incorrect email_id";
		}

	}

	@Override
	public EmployeeDto addEmpskill(int id, SkillList skillList) {

		List<EmpSkillDto> empSkillDtoList = new ArrayList<EmpSkillDto>();
		for (Skills skill : skillList.getSkilllist()) {

			SkillDto skillDto = skillsRepo.findBySkillName(skill.getSkillName());
			empSkillDtoList.add(new EmpSkillDto(skillDto.getSkillid(), skillDto.getSkillName(), skill.getExperiance()));
		}

		// Get an employee object of pathvariable id to inject into empskill object
		EmployeeDto employee = employeeReo.findById(id);
		if (employee != null) {
			for (EmpSkillDto skill : empSkillDtoList) {
				skill.setEmployee(employee);
				empSkillRepo.save(skill);
			}

			// return empSkillDtoList;

			return employeeReo.findById(id);
		}

		else {
			return null;
		}
	}

	@Override
	public List<EmployeeDto> getAllEmp() {

		return employeeReo.findAll();
	}

	@Override
	public EmployeeDto getEmpById(int id) {

		return employeeReo.findById(id);
	}

	@Override
	public String deleteEmp(int id) {
		EmployeeDto empToBeDeleted = employeeReo.findById(id);

		try {
			employeeReo.delete(empToBeDeleted);
			return "Success";
		} catch (Exception e) {
			if (empToBeDeleted == null)

				return " Employee doesn't exist";
			else {
				e.printStackTrace();
				return e.toString();
			}

		}

	}

	@Override
	public String updateEmpSkill(int id, String skillName, Skills skill) {

		EmployeeDto employee = employeeReo.findById(id);
		try {
			EmpSkillDto empSkillToBeUpdated = empSkillRepo.findBySkillNameAndEmployee(skillName, employee);
			if (empSkillToBeUpdated != null) {
				// update existing skill
				empSkillToBeUpdated.setExperiance(skill.getExperiance());
				empSkillRepo.save(empSkillToBeUpdated);

			}

			else {
				SkillDto skillDto = skillsRepo.findBySkillName(skillName);
				EmpSkillDto newEmpSkillDto = new EmpSkillDto();
				newEmpSkillDto.setSkillid(skillDto.getSkillid());
				newEmpSkillDto.setSkillName(skillDto.getSkillName());
				newEmpSkillDto.setExperiance(skill.getExperiance());
				newEmpSkillDto.setEmployee(employee);

				empSkillRepo.save(newEmpSkillDto);

			}

			return "success";
		}

		catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}

	}

	@Override
	public EmployeeDto updateEmpSkillSet(int id, SkillList skillList) {
		EmployeeDto employee = employeeReo.findById(id);
		try {
			for (Skills skillToBeupdatedWith : skillList.getSkilllist()) {
				EmpSkillDto empSkillToBeUpdated = empSkillRepo
						.findBySkillNameAndEmployee(skillToBeupdatedWith.getSkillName(), employee);
				if (empSkillToBeUpdated != null) {
					empSkillToBeUpdated.setExperiance(skillToBeupdatedWith.getExperiance());
					empSkillRepo.save(empSkillToBeUpdated);
				}

				else {

					SkillDto skillDto = skillsRepo.findBySkillName(skillToBeupdatedWith.getSkillName());
					EmpSkillDto newEmpSkillDto = new EmpSkillDto();
					newEmpSkillDto.setSkillid(skillDto.getSkillid());
					newEmpSkillDto.setSkillName(skillDto.getSkillName());
					newEmpSkillDto.setExperiance(skillToBeupdatedWith.getExperiance());
					newEmpSkillDto.setEmployee(employee);

					empSkillRepo.save(newEmpSkillDto);
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return employeeReo.findById(id);
	}

	@Override
	public EmpSkillDto deleteEmpSkillData(int empid, int skillid) {
		
		EmployeeDto employee = employeeReo.findById(empid);
		EmpSkillDto empSkillDtoToBeDeleted = empSkillRepo.findByEmployeeAndSkillid(employee, skillid);
		empSkillRepo.delete(empSkillDtoToBeDeleted);
		return empSkillDtoToBeDeleted;
	}

}
