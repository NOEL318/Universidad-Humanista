package com.intranet.member.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tutor")
public class ParentEntity {
	public ParentEntity() {
	}

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	private UUID userId;
	private List<UUID> childrenIds; // ID's a sus hijos-> (Student)
	private String relationship;
	private Boolean hasLegalCustody; //Tiene la custodia legal?


}
