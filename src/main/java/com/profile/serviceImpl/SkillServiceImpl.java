package com.profile.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profile.Repository.SkillsRepo;
import com.profile.dto.SkillDto;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	SkillsRepo skillsRepo;

	@Override
	public List<SkillDto> addSkill(SkillList skillList) {

		List<SkillDto> skillDtolist = new ArrayList<SkillDto>();

		for (Skills skill : skillList.getSkilllist()) {
			SkillDto skillDto = new SkillDto();
			skillDto.setSkillName(skill.getSkillName());
			skillDtolist.add(skillDto);
		}
		return skillsRepo.saveAll(skillDtolist);
	}

	public List<SkillDto> getSkillList() {

		return skillsRepo.findAll();

	}

	@Override
	public SkillDto findBySkillId(int id) {

		return skillsRepo.findBySkillid(id);
	}

	@Override
	public SkillDto updateSkill(int skillId, Skills skill) {

		SkillDto existSkillDto = skillsRepo.findBySkillid(skillId);

		if (existSkillDto == null) {

			SkillDto newSkillDto = new SkillDto();
			newSkillDto.setSkillName(skill.getSkillName());
			newSkillDto.setSkillid(skillId);
			skillsRepo.save(newSkillDto);
			return newSkillDto;
		} else {
			// newSkillsDto.setId(skillsDto.getId());
			//existSkillDto.setSkillid(skill.getSkillid());
			 existSkillDto.setSkillName(skill.getSkillName());

			skillsRepo.save(existSkillDto);

		}
		return existSkillDto;
	}

	@Override
	public String deleteSkill(String skillname) {

		SkillDto skillToBeDeleted = skillsRepo.findBySkillName(skillname);

		try {
			skillsRepo.delete(skillToBeDeleted);
			return "Success";
		} catch (Exception e) {
			if (skillToBeDeleted == null)

				return " Skill doesn't exist";
			else {
				e.printStackTrace();
				return e.toString();
			}

		}

	}

}
