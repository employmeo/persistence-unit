package com.employmeo.data.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "surveys")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"surveyQuestions","surveySections"})
@ToString(exclude={"surveyQuestions","surveySections"})
public class Survey implements Serializable {

	@Transient
	private static final long serialVersionUID = -133035016766293056L;

	@Id
	@Basic(optional = false)
	@Column(name = "survey_id")
	private Long id;

	@Column(name = "survey_name")
	private String name;

	@Column(name = "survey_description")
	private String description;

	@Column(name = "survey_status")
	private Integer surveyStatus;

	@Column(name = "survey_type")
	private Integer surveyType;

	@Column(name = "survey_list_price")
	private Double listPrice;

	@Column(name = "survey_completion_time")
	private Long completionTime;

	@Column(name = "survey_completion_pct")
	private Double completionPercent;

	@Column(name = "survey_foreign_id")
	private String foreignId;

	// bi-directional many-to-one association to SurveyQuestion
	@JsonManagedReference
	@OneToMany(mappedBy = "survey", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<SurveyQuestion> surveyQuestions = new HashSet<>();

	// bi-directional many-to-one association to SurveyQuestion
	@JsonManagedReference
	@OneToMany(mappedBy = "survey", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<SurveySection> surveySections = new HashSet<>();

}
