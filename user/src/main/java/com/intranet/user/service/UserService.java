package com.intranet.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intranet.user.entity.UserEntity;
import com.intranet.user.mapper.UserMapper;
import com.intranet.user.model.UserModel;
import com.intranet.user.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	public UserModel createUserToDataBase(UserModel user) {
		UserEntity user_entity = userMapper.UserModelToEntity(user);
		UserEntity response = userRepository.save(user_entity);
		return userMapper.UserEntityToModel(response);
	}

	public List<UserModel> getUsersFromDatabase() {
		List<UserEntity> users = userRepository.findAll();
		List<UserModel> users_model_list = userMapper.UserEntityToModel(users);
		return users_model_list;
	}

	public List<UserModel> getUsersFromDatabaseByIds(List<UUID> ids) {
		List<UserEntity> users = userRepository.findAllById(ids);
		List<UserModel> users_model_list = userMapper.UserEntityToModel(users);
		return users_model_list;
	}

	public UserModel getUserFromDatabase(String id) {
		UserEntity users = userRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		UserModel users_model = userMapper.UserEntityToModel(users);
		return users_model;
	}

	public UserModel editUserInDatabase(UserModel user) {
		UserEntity user_entity = userMapper.UserModelToEntity(user);
		// Exist?
		userRepository.findById(user_entity.getId())
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		UserEntity results = userRepository.save(user_entity);
		UserModel user_model = userMapper.UserEntityToModel(results);
		return user_model;
	}

	public UserModel deleteUserFromDatabase(String id) {
		UUID uuid = UUID.fromString(id);
		UserEntity userentity = userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("User Not Found"));
		UserModel usermodel = userMapper.UserEntityToModel(userentity);
		userRepository.deleteById(uuid);
		return usermodel;
	}

}
