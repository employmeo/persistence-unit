package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "survey_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyQuestion implements Serializable {

	@Transient
	private static final long serialVersionUID = -7227663971810034513L;

	@Id
	@Basic(optional = false)
	@Column(name = "SQ_ID")
	private Long id;

	@Column(name = "SQ_DEPENDENCY")
	private Boolean dependency;

	@Column(name = "SQ_REQUIRED")
	private Boolean required;

	@Column(name = "SQ_SEQUENCE")
	private Integer sequence;

	@Column(name = "SQ_PAGE")
	private Integer page;

	// bi-directional many-to-one association to Question
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "SQ_QUESTION_ID")
	private Question question;

	// bi-directional many-to-one association to Survey
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SURVEY_ID")
	private Survey survey;

}
