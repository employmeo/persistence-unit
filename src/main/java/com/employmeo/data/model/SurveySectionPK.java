package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveySectionPK implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 4935821617613015469L;

	@Column(name="ss_survey_id")
	private Long surveyId;

	@Column(name="ss_survey_section")
	private Integer sectionNumber;

}
