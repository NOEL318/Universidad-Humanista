package com.intranet.member.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.intranet.member.entity.TeacherEntity;
import com.intranet.member.mapper.TeacherMapper;
import com.intranet.member.model.TeacherModel;
import com.intranet.member.model.UserModel;
import com.intranet.member.repository.TeacherRepository;

@Service
public class TeacherService {
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private TeacherMapper teacherMapper;

	@Value("${services.user}")
	private String userUrl;
	private RestClient restClient = RestClient.builder().build();

	public List<TeacherModel> findAll() {
		List<TeacherModel> teachers = teacherMapper.TeacherEntityToModel(teacherRepository.findAll());
		List<UUID> userIds = teachers.stream().map(TeacherModel::getUserId).distinct().toList();
		List<UserModel> users = restClient.post().uri(userUrl + "/user/allById").body(userIds)
				.retrieve().body(new ParameterizedTypeReference<List<UserModel>>() {
				});
		teachers.forEach(t -> t.setUser(users.stream()
				.filter(u -> u.getId().equals(t.getUserId()))
				.findFirst()
				.orElse(null)));
		return teachers;
	}

	public TeacherModel find(String id) {
		TeacherEntity entity = teacherRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new IllegalArgumentException("Teacher Not Found"));
		return withUser(teacherMapper.TeacherEntityToModel(entity));
	}

	public TeacherModel findByUserId(UUID userId) {
		TeacherEntity entity = teacherRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("Teacher Not Found"));
		return withUser(teacherMapper.TeacherEntityToModel(entity));
	}

	public TeacherModel create(TeacherModel model) {
		TeacherEntity saved = teacherRepository.save(teacherMapper.TeacherModelToEntity(model));
		return teacherMapper.TeacherEntityToModel(saved);
	}

	public TeacherModel update(String id, TeacherModel model) {
		model.setId(UUID.fromString(id));
		teacherRepository.findById(model.getId()).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found"));
		TeacherEntity saved = teacherRepository.save(teacherMapper.TeacherModelToEntity(model));
		return teacherMapper.TeacherEntityToModel(saved);
	}

	public TeacherModel delete(String id) {
		TeacherEntity entity = teacherRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new IllegalArgumentException("Teacher Not Found"));
		teacherRepository.delete(entity);
		return teacherMapper.TeacherEntityToModel(entity);
	}

	private TeacherModel withUser(TeacherModel model) {
		model.setUser(restClient.get().uri(userUrl + "/user/" + model.getUserId()).retrieve().body(UserModel.class));
		return model;
	}
}
