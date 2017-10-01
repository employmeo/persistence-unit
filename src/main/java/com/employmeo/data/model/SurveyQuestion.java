package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "survey_questions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"question","survey"})
@ToString(exclude={"question","survey"})
public class SurveyQuestion implements Serializable, Comparable<SurveyQuestion> {

	@Transient
	private static final long serialVersionUID = -7227663971810034513L;

	@Id
	//@Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sq_id_seq")
    @SequenceGenerator(name="sq_id_seq", sequenceName="survey_questions_sq_id_seq", allocationSize=1)
	@Column(name = "SQ_ID")
	private Long id;

	@Column(name = "SQ_DEPENDENCY")
	private Boolean dependency = false;

	@Column(name = "SQ_REQUIRED")
	private Boolean required = true;

	@Column(name = "SQ_SEQUENCE")
	private Integer sequence;

	@Column(name = "SQ_PAGE")
	private Integer page;

	@Column(name = "SQ_QUESTION_ID")
	private Long questionId;

	// bi-directional many-to-one association to Question (removed json managed reference)
	@ManyToOne
	@JoinColumn(name = "SQ_QUESTION_ID", insertable=false, updatable=false)
	private Question question;

	// bi-directional many-to-one association to Survey
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SURVEY_ID", insertable=false, updatable=false)
	private Survey survey;

	@Column(name = "SURVEY_ID")
	private Long surveyId;

	public int compareTo(SurveyQuestion obj) {
    	int result = this.getPage() - obj.getPage();
    	if (result == 0) result = this.getSequence() - obj.getSequence();
    	return result;
	}
	
}
