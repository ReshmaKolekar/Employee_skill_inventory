package com.profile.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.dao.EmpSkillDaoImpl;
import com.profile.dto.SkillDto;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.SkillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/skill")
@Api(value = "Skill Resource ", description = "Skill data management")
public class SkillController {

	@Autowired
	EmpSkillDaoImpl empSkillDaoImpl;

	@Autowired
	SkillService skillService;

	@ApiOperation(value = "Add Skills")
	@PostMapping("/addSkills")
	public ResponseEntity<List<SkillDto>> addSkill(@RequestBody SkillList skillList) {

		List<SkillDto> skillDtoList = skillService.addSkill(skillList);
		return ResponseEntity.ok().body(skillDtoList);
	}

	@ApiOperation(value = "Returns all skills ")
	@GetMapping("/skillList")
	public ResponseEntity<List<SkillDto>> getSkillList() {

		List<SkillDto> skillDtoList = skillService.getSkillList();
		return ResponseEntity.ok().body(skillDtoList);

	}

	@ApiOperation(value = "Returns skill data by id ")
	@GetMapping("/skill/{skillId}")
	public ResponseEntity<SkillDto> findBySkillId(@PathVariable(value = "skillId") int id) {

		SkillDto skillsDto = skillService.findBySkillId(id);

		if (skillsDto == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(skillsDto);
	}

	@ApiOperation(value = "Update skill")
	@PutMapping("/update/skill/{skillId}")
	public ResponseEntity<SkillDto> updateSkill(@PathVariable(value = "skillId") int skillId,
			@RequestBody Skills skill) {

		SkillDto skillDto = skillService.updateSkill(skillId, skill);

		return ResponseEntity.ok().body(skillDto);
	}

	@ApiOperation(value = "Delete skill")
	@DeleteMapping("/delete/skill/{skillname}")
	public ResponseEntity<String> delete(@PathVariable(value = "skillname") String skillname) {

		String response = skillService.deleteSkill(skillname);
		if (response.equalsIgnoreCase("success"))
			return ResponseEntity.ok().body(response);

		return ResponseEntity.notFound().build();

	}

}
