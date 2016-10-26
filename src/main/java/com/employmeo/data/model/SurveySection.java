package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "survey_sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveySection implements Serializable {

	@Transient
	private static final long serialVersionUID = -5557063229864908810L;

	@EmbeddedId
	private SurveySectionPK id;

	@Column(name="ss_all_required")
	private Boolean allRequired;

	@Column(name="ss_instructions")
	private String instructions;

	@Column(name="ss_questions_per_page")
	private Integer questionsPerPage;

	@Column(name="ss_time_seconds")
	private Integer timeSeconds;

	// bi-directional many-to-one association to Survey
	@ManyToOne
	@JoinColumn(name = "ss_survey_id", insertable=false, updatable=false)
	private Survey survey;

}
