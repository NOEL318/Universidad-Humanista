package com.intranet.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.intranet.member.entity.TeacherEntity;
import com.intranet.member.model.TeacherModel;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherEntity TeacherModelToEntity(TeacherModel teacher);

    @Mapping(target = "user", ignore = true)
    TeacherModel TeacherEntityToModel(TeacherEntity teacher);

    List<TeacherModel> TeacherEntityToModel(List<TeacherEntity> entities);
}
