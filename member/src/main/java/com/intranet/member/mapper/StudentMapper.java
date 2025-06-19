package com.intranet.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.intranet.member.entity.StudentEntity;
import com.intranet.member.model.StudentModel;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    
    StudentEntity StudentModelToEntity(StudentModel user);
    StudentModel StudentEntityToModel(StudentEntity user);

    List<StudentEntity> StudentModelToEntity(List<StudentModel> models);
    List<StudentModel> StudentEntityToModel(List<StudentEntity> entities);
}
