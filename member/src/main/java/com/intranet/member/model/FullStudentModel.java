package com.intranet.member.model;

import lombok.Data;

@Data
public class FullStudentModel {
	
	private StudentModel memberStudentModel;
	private UserModel usermodel;

	public FullStudentModel(StudentModel model, UserModel user) {
		this.memberStudentModel = model;
		this.usermodel = user;
	}
}
