package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"question"})
@ToString(exclude={"question"})
public class Answer implements Serializable {

	@Transient
	private static final long serialVersionUID = 378095961034605750L;

	@Id
	@Basic(optional = false)
	@Column(name = "ANSWER_ID")
	private String id;

	@Column(name = "ANSWER_DESCRIPTION")
	private String description;

	@Column(name = "ANSWER_DISPLAY_ID")
	private Long displayId;

	@Column(name = "ANSWER_TEXT")
	private String answerText;

	@Column(name = "ANSWER_VALUE")
	private Integer answerValue;

	// bi-directional many-to-one association to Question
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ANSWER_QUESTION_ID", insertable=false, updatable=false)
	private Question question;

	@Column(name = "ANSWER_QUESTION_ID")
	private Long questionId;
}
