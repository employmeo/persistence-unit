package com.employmeo.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "question_types")
@Data
@NoArgsConstructor
public class QuestionType implements Serializable {

	@Transient
	private static final long serialVersionUID = -8672423095201058881L;
	
	@Id
	@Basic(optional = false)
	@Column(name = "QUESTION_TYPE_ID")
	private Long id;

	@Column(name = "QUESTION_TYPE_NAME")
	private String name;

	@Column(name = "QUESTION_TYPE_DESCRIPTION")
	private String description;

	@Column(name = "MODIFIED_DATE")
	private Timestamp modifiedDate;

}
