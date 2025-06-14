package com.intranet.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intranet.user.entities.UserEntity;
import com.intranet.user.mappers.UserMapper;
import com.intranet.user.models.UserModel;
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

}
