package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "criteria")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"graderQuestion"})
@ToString(exclude={"graderQuestion"})
public class Criterion implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 4400074459392142206L;

	@Id
	@Basic(optional = false)
	@Column(name = "CRITERION_ID")
	private Long id;

	@Column(name = "CRITERION_REQUIRED")
	private Boolean required;

	@Column(name = "CRITERION_SEQ")
	private Integer sequence;

	@Column(name = "CRITERION_QUESTION_ID")
	private Long surveyQuestionId;

	@Column(name = "CRITERION_LINKED_QUESTION_ID")
	private Long graderQuestionId;
	
	// bi-directional many-to-one association to Question
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "CRITERION_LINKED_QUESTION_ID", insertable=false, updatable=false)
	private Question graderQuestion;

	
}
