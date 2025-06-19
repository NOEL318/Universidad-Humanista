package com.intranet.member.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intranet.member.client.StudentClient;
import com.intranet.member.entity.StudentEntity;
import com.intranet.member.mapper.StudentMapper;
import com.intranet.member.model.FullStudentModel;
import com.intranet.member.model.StudentModel;
import com.intranet.member.model.UserModel;
import com.intranet.member.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private StudentClient studentClient;

	public FullStudentModel findUser(String id) {
		StudentEntity entity = studentRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new IllegalArgumentException("Something Went Wrong here"));
		StudentModel model = studentMapper.StudentEntityToModel(entity);
		System.out.println(model + "esta es mi model");
		UserModel user = studentClient.getUserById(model.getUserId());
		System.out.println(user + "esta es mi user");
		System.out.println(user.getId() + "esta es mi user");
		FullStudentModel newuser = new FullStudentModel(model, user);
		System.out.println(newuser);
		return newuser;
	}

	public List<FullStudentModel> findAllUsers() {
		List<StudentEntity> students_list = studentRepository.findAll();
		List<StudentModel> student_list_model = studentMapper.StudentEntityToModel(students_list);
		System.out.println(students_list + "entity");

		System.out.println(student_list_model + "listmodeeel");
		// Extract ids from list of students
		List<UUID> userIds = student_list_model.stream()
				.map(StudentModel::getUserId)
				.distinct()
				.collect(Collectors.toList());
		// call to user service to get users
		List<UserModel> userModels = studentClient.getUsersListById(userIds);
		// Fusion of data
		List<FullStudentModel> fullstudentlist = student_list_model.stream()
				.map(student -> new FullStudentModel(
						student,
						userModels.stream()
								.filter(u -> u.getId().equals(student.getUserId()))
								.findFirst()
								.orElse(null)))
				.collect(Collectors.toList());
		return fullstudentlist;
	}
}
