package com.intranet.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	@CacheEvict(value = "users", allEntries = true)
	public UserModel createUserToDataBase(UserModel user) {
		UserEntity user_entity = userMapper.UserModelToEntity(user);
		user_entity.setEmail(user.getEmail().trim().toLowerCase());
		user_entity.setPassword(passwordEncoder.encode(user.getPassword()));
		UserEntity response = userRepository.save(user_entity);
		return userMapper.UserEntityToModel(response);
	}

	@Cacheable(value = "users", key = "'all'")
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

	@Cacheable(value = "users", key = "#id")
	public UserModel getUserFromDatabase(String id) {
		UserEntity users = userRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		UserModel users_model = userMapper.UserEntityToModel(users);
		return users_model;
	}

	@CacheEvict(value = "users", allEntries = true)
	public UserModel editUserInDatabase(UserModel user) {
		UserEntity user_entity = userMapper.UserModelToEntity(user);
		// Exist?
		UserEntity current = userRepository.findById(user_entity.getId())
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		// Si no mandan contraseña se queda la que tenia
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			user_entity.setPassword(current.getPassword());
		} else {
			user_entity.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		user_entity.setRegistry_date(current.getRegistry_date());
		UserEntity results = userRepository.save(user_entity);
		UserModel user_model = userMapper.UserEntityToModel(results);
		return user_model;
	}

	@CacheEvict(value = "users", allEntries = true)
	public UserModel deleteUserFromDatabase(String id) {
		UUID uuid = UUID.fromString(id);
		UserEntity userentity = userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("User Not Found"));
		UserModel usermodel = userMapper.UserEntityToModel(userentity);
		userRepository.deleteById(uuid);
		return usermodel;
	}

}
