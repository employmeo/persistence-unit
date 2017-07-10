package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

	public static final int TYPE_WEB = 1;
	public static final int TYPE_PHONE = 2;
	public static final int TYPE_MULTI = 3;

	public static final int AVAIL_FREE = 100;
	public static final int AVAIL_SMB_TURN = 200;
	public static final int AVAIL_SMB_PERF = 300;
	public static final int AVAIL_SMB_CULT = 400;
	public static final int AVAIL_CUSTOM = 500;
	public static final int AVAIL_NONE = 999;
	
	@Id
	@Basic(optional = false)
	@Column(name = "survey_id")
	private Long id;

	@Column(name = "survey_name")
	private String name;

	@Column(name = "survey_description")
	private String description;
	
	@Column(name = "survey_oneliner")
	private String oneLiner;

	@Column(name = "survey_status")
	private Integer surveyStatus = 1;

	@Column(name = "survey_availability")
	private Integer availability = AVAIL_NONE;
	
	@Column(name = "survey_type")
	private Integer surveyType = TYPE_WEB;

	@Column(name = "survey_list_price")
	private Double listPrice;

	@Column(name = "survey_default_models")
	private String defaultModels;

	@Column(name = "survey_completion_time")
	private Long completionTime;

	@Column(name = "survey_completion_pct")
	private Double completionPercent;

	@Column(name = "survey_foreign_id")
	private String foreignId;

	// bi-directional many-to-one association to SurveyQuestion
	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "survey", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<SurveyQuestion> surveyQuestions = new HashSet<>();

	// bi-directional many-to-one association to SurveyQuestion
	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "survey", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<SurveySection> surveySections = new HashSet<>();

	@Column(name = "survey_modified_date")
	private Date modifiedDate;

	@PreUpdate
	void setModifiedDate() {
		this.modifiedDate = new Date();
	}

}
