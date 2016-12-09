package com.employmeo.data.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"answers","responses","surveyQuestions"})
@ToString(exclude={"answers","responses","surveyQuestions"})
public class Question implements Serializable {

	@Transient
	private static final long serialVersionUID = 1234838724006105384L;

	@Id
	@Basic(optional = false)
	@Column(name = "QUESTION_ID")
	private Long questionId;

	// Integer ?????
	@Column(name = "MODIFIED_DATE")
	private Integer modifiedDate = 0;

	@Column(name = "QUESTION_DESCRIPTION")
	private String description;

	@Column(name = "QUESTION_DISPLAY_ID")
	private Long displayId = 1l;//Defaulted to 1

	@Column(name = "QUESTION_TEXT")
	private String questionText;
	
	@Column(name = "QUESTION_MEDIA")
	private String questionMedia;

	@Column(name = "QUESTION_TYPE")
	private Integer questionType;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "QUESTION_TYPE", insertable = false, updatable = false)
	private QuestionType type;

	@Column(name = "QUESTION_COREFACTOR_ID")
	private Integer corefactorId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "QUESTION_COREFACTOR_ID", insertable = false, updatable = false)
	private Corefactor corefactor;

	@Column(name = "QUESTION_DIRECTION")
	private Integer direction;

	@Column(name = "question_foreign_id")
	private Integer foreignId;

	@Column(name = "question_foreign_source")
	private String foreignSource;

	// bi-directional many-to-one association to Answer
	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Answer> answers = new HashSet<>();

	// bi-directional many-to-one association to Response
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "question")
	private Set<Response> responses = new HashSet<>();

	// bi-directional many-to-one association to SurveyQuestion
	@JsonBackReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "question")
	private Set<SurveyQuestion> surveyQuestions = new HashSet<>();
	
	@JsonProperty("type")
	public String getType() {
		if(type != null) return type.getName();
		return null;
	}
	
	@JsonProperty("corefactorName")
	public String getCorefactorName() {
		if (corefactor !=null) return corefactor.getName();
		return null;
	}

}
