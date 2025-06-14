package com.intranet.user.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.intranet.user.entities.UserEntity;
import com.intranet.user.models.UserModel;

//Library used: https://mapstruct.org/
@Mapper(componentModel = "spring")
public interface UserMapper {

	// Individual User
	UserEntity UserModelToEntity(UserModel user);

	UserModel UserEntityToModel(UserEntity user);

	// List of Users
	List<UserEntity> UserModelToEntity(List<UserModel> models);
	List<UserModel> UserEntityToModel(List<UserEntity> entities);

}
