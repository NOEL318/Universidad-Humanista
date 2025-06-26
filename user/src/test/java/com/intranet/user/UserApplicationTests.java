package com.intranet.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.intranet.user.model.UserModel;
import com.intranet.user.entity.UserEntity;
import com.intranet.user.service.UserService;
import com.intranet.user.repository.UserRepository;
import com.intranet.user.mapper.UserMapper;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserApplicationTests {

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserMapper userMapper;
	@InjectMocks
	private UserService userService;

	@Test
	void createUserToDataBase_success() {
		UserModel mockModel = new UserModel();
		mockModel.setName("Test");
		mockModel.setFather_surname("Apellido");
		mockModel.setMother_surname("Apellido2");
		mockModel.setBorn_date("2000-01-01");
		mockModel.setPhone("1234567890");
		mockModel.setSex("M");
		mockModel.setAddress("Calle 1");
		mockModel.setPhoto_url("url");
		mockModel.setRole("user");
		mockModel.setCurp("CURP123");
		mockModel.setEmail("test@email.com");
		mockModel.setPassword("pass");

		UserEntity mockEntity = new UserEntity();
		when(userMapper.UserModelToEntity(any(UserModel.class))).thenReturn(mockEntity);
		when(userRepository.save(any(UserEntity.class))).thenReturn(mockEntity);
		when(userMapper.UserEntityToModel(any(UserEntity.class))).thenReturn(mockModel);

		UserModel result = userService.createUserToDataBase(mockModel);
		assertNotNull(result);
		assertEquals("Test", result.getName());
	}

	@Test
	void createUserToDataBase_error() {
		UserModel mockModel = new UserModel();
		when(userMapper.UserModelToEntity(any(UserModel.class))).thenThrow(new RuntimeException("Mapping error"));
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.createUserToDataBase(mockModel);
		});
		assertEquals("Mapping error", exception.getMessage());
	}

	@Test
	void getUsersFromDatabase_success() {
		List<UserEntity> entityList = List.of(new UserEntity());
		List<UserModel> modelList = List.of(new UserModel());
		when(userRepository.findAll()).thenReturn(entityList);
		when(userMapper.UserEntityToModel(entityList)).thenReturn(modelList);
		List<UserModel> result = userService.getUsersFromDatabase();
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void getUsersFromDatabase_error() {
		when(userRepository.findAll()).thenThrow(new RuntimeException("DB error"));
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.getUsersFromDatabase();
		});
		assertEquals("DB error", exception.getMessage());
	}

	@Test
	void getUsersFromDatabaseByIds_success() {
		List<UUID> ids = List.of(UUID.randomUUID());
		List<UserEntity> entityList = List.of(new UserEntity());
		List<UserModel> modelList = List.of(new UserModel());
		when(userRepository.findAllById(ids)).thenReturn(entityList);
		when(userMapper.UserEntityToModel(entityList)).thenReturn(modelList);
		List<UserModel> result = userService.getUsersFromDatabaseByIds(ids);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void getUsersFromDatabaseByIds_error() {
		List<UUID> ids = List.of(UUID.randomUUID());
		when(userRepository.findAllById(ids)).thenThrow(new RuntimeException("DB error"));
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.getUsersFromDatabaseByIds(ids);
		});
		assertEquals("DB error", exception.getMessage());
	}

	@Test
	void getUserFromDatabase_success() {
		UUID id = UUID.randomUUID();
		UserEntity entity = new UserEntity();
		UserModel model = new UserModel();
		when(userRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
		when(userMapper.UserEntityToModel(entity)).thenReturn(model);
		UserModel result = userService.getUserFromDatabase(id.toString());
		assertNotNull(result);
	}

	@Test
	void getUserFromDatabase_error() {
		UUID id = UUID.randomUUID();
		when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.getUserFromDatabase(id.toString());
		});
		assertEquals("User Not Found", exception.getMessage());
	}

	@Test
	void editUserInDatabase_success() {
		UserModel model = new UserModel();
		UserEntity entity = new UserEntity();
		when(userMapper.UserModelToEntity(model)).thenReturn(entity);
		when(userRepository.findById(entity.getId())).thenReturn(java.util.Optional.of(entity));
		when(userRepository.save(entity)).thenReturn(entity);
		when(userMapper.UserEntityToModel(entity)).thenReturn(model);
		UserModel result = userService.editUserInDatabase(model);
		assertNotNull(result);
	}

	@Test
	void editUserInDatabase_error_not_found() {
		UserModel model = new UserModel();
		UserEntity entity = new UserEntity();
		when(userMapper.UserModelToEntity(model)).thenReturn(entity);
		when(userRepository.findById(entity.getId())).thenReturn(java.util.Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.editUserInDatabase(model);
		});
		assertEquals("User Not Found", exception.getMessage());
	}

	@Test
	void deleteUserFromDatabase_success() {
		UUID id = UUID.randomUUID();
		UserEntity entity = new UserEntity();
		UserModel model = new UserModel();
		when(userRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
		when(userMapper.UserEntityToModel(entity)).thenReturn(model);
		doNothing().when(userRepository).deleteById(id);
		UserModel result = userService.deleteUserFromDatabase(id.toString());
		assertNotNull(result);
	}

	@Test
	void deleteUserFromDatabase_error_not_found() {
		UUID id = UUID.randomUUID();
		when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userService.deleteUserFromDatabase(id.toString());
		});
		assertEquals("User Not Found", exception.getMessage());
	}

}
