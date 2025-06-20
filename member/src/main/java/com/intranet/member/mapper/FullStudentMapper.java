package com.intranet.member.mapper;

import com.intranet.member.model.FullStudentModel;
import com.intranet.member.model.StudentModel;
import com.intranet.member.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface FullStudentMapper {
	// Hago mappings de los ids que me dan error
	@Mappings({
			@Mapping(source = "student.id", target = "id"),
			@Mapping(source = "student.user_id", target = "user_id"),
			@Mapping(source = "student.status", target = "status")
	})
	FullStudentModel toFullStudent(StudentModel student, UserModel user);
}
