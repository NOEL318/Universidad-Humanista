package com.intranet.member.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.intranet.member.entity.StudentEntity;
import com.intranet.member.mapper.FullStudentMapper;
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
	private FullStudentMapper fullStudentMapper;


	@Autowired
	private DiscoveryClient discoveryClient;
	private RestClient restClient = RestClient.builder().build();

	private Logger log = LogManager.getLogger(StudentService.class);

	public StudentModel createStudentToDatabase(StudentModel model) {
		StudentEntity user = studentMapper.StudentModelToEntity(model);
		StudentEntity response = studentRepository.save(user);
		return studentMapper.StudentEntityToModel(response);
	}

	public FullStudentModel findUser(String id) {
		StudentEntity entity = studentRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new IllegalArgumentException("Something Went Wrong here"));
		StudentModel model = studentMapper.StudentEntityToModel(entity);
		System.out.println(model + "esta es mi model");

		// "/user/{id}"
		ServiceInstance serviceInstance = discoveryClient.getInstances("user").get(0);
		String url = serviceInstance.getUri() + "/user/" + model.getUser_id();
		UserModel user = restClient.get().uri(url).retrieve().body(UserModel.class);
		System.out.println(user + "esta es mi user");
		System.out.println(user.getId() + "esta es mi user");
		FullStudentModel newuser = fullStudentMapper.toFullStudent(model, user);
		return newuser;
	}

	public StudentModel updateStudentFromDatabase(String id, StudentModel newuser) {
		StudentEntity student = studentMapper.StudentModelToEntity(newuser);
		// Exist?
		studentRepository.findById(student.getId()).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		StudentEntity results = studentRepository.save(student);
		return studentMapper.StudentEntityToModel(results);
	}

	public StudentModel deleteStudentFromDatabase(String id) {
		StudentEntity student = studentRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		StudentModel deleted = studentMapper.StudentEntityToModel(student);
		studentRepository.deleteById(UUID.fromString(id));
		return deleted;
	}

	public List<FullStudentModel> findAllUsers() {
		List<StudentEntity> students_list = studentRepository.findAll();
		List<StudentModel> student_list_model = studentMapper.StudentEntityToModel(students_list);
		log.info("Students List: {}", student_list_model);
		// Extract ids from list of students
		List<UUID> userIds = student_list_model.stream()
				.map(StudentModel::getUser_id)
				.distinct()
				.collect(Collectors.toList());

		ServiceInstance serviceInstance = discoveryClient.getInstances("user").get(0);
		List<UserModel> userModels = restClient.post().uri(serviceInstance.getUri() + "/user/allById").body(userIds)
				.retrieve().body(new org.springframework.core.ParameterizedTypeReference<List<UserModel>>() {
				});
		// Fusion of data
		List<FullStudentModel> fullstudentlist = student_list_model.stream()
				.map(student -> fullStudentMapper
						.toFullStudent(
								student,
								userModels.stream()
										.filter(u -> u.getId().equals(student.getUser_id()))
										.findFirst()
										.orElse(null)))
				.collect(Collectors.toList());
		return fullstudentlist;
	}
}
