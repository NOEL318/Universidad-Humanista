
package com.intranet.member.client;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import com.intranet.member.model.UserModel;

@Component
public class StudentClientFallback implements StudentClient {
	@Override
	public UserModel getUserById(UUID id) {
		UserModel fallbackuser = new UserModel();
		fallbackuser.setId(id);
		fallbackuser.setName("Unavailable name");
		return fallbackuser;
	}

	@Override
	public List<UserModel> getUsersListById(List<UUID> ids) {
		// emptylist
		return Collections.emptyList();
	}

}
