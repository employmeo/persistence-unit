package com.employmeo.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "respondants")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"account","accountSurvey","partner","position","location","person","responses","respondantScores"})
@ToString(exclude={"account","accountSurvey","partner","position","location","person","responses","respondantScores"})
public class Respondant implements Serializable {

	@Transient
	private static final long serialVersionUID = 808486275173441348L;

	public static final int STATUS_INVITED = 1;
	public static final int STATUS_STARTED = 5;
	public static final int STATUS_COMPLETED = 10;
	public static final int STATUS_SCORED = 13;
	public static final int STATUS_PREDICTED = 15;
	public static final int STATUS_REJECTED = 16;
	public static final int STATUS_OFFERED = 17;
	public static final int STATUS_DECLINED = 18;
	public static final int STATUS_HIRED = 20;
	public static final int STATUS_QUIT = 30;
	public static final int STATUS_TERMINATED = 40;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "respondant_id")
	private Long id;

	@Column(name = "respondant_uuid", insertable = true, updatable = false)
	@Convert(disableConversion = true)  // hibernate specific mapping
	@Type(type="pg-uuid") // hibernate specific mapping
	private UUID respondantUuid;

	// bi-directional many-to-one association to Account
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_account_id", insertable = false, updatable = false)
	private Account account;

	@Column(name = "respondant_account_id", insertable = true, updatable = false)
	private Long accountId;

	@Column(name = "respondant_created_date", insertable = false, updatable = false)
	private Date createdDate;

	@Column(name = "respondant_status")
	private Integer respondantStatus = Respondant.STATUS_INVITED;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_asid", insertable = false, updatable = false)
	private AccountSurvey accountSurvey;

	@Column(name = "respondant_asid", insertable = true, updatable = false)
	private Long accountSurveyId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_partner_id", insertable = false, updatable = false)
	private Partner partner;

	@Column(name = "respondant_partner_id", insertable = true, updatable = true)
	private Integer partnerId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_position_id", insertable = false, updatable = false)
	private Position position;

	@Column(name = "respondant_position_id", insertable = true, updatable = true)
	private Long positionId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_location_id", insertable = false, updatable = false)
	private Location location;

	@Column(name = "respondant_location_id", insertable = true, updatable = true)
	private Long locationId;

	// bi-directional many-to-one association to Person
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_person_id", insertable=false, updatable=false)
	private Person person;

	@Column(name = "respondant_person_id")
	private Long personId;

	// bi-directional many-to-one association to Responses
	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "respondant", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Response> responses = new HashSet<>();

	// bi-directional many-to-one association to Responses
	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "respondant", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<RespondantScore> respondantScores = new HashSet<>();

	// Scoring info
	@Column(name = "respondant_profile")
	private String profileRecommendation;

	@Column(name = "respondant_composite_score")
	private Double compositeScore;

	@Column(name = "respondant_profile_a")
	private Double profileA;

	@Column(name = "respondant_profile_b")
	private Double profileB;

	@Column(name = "respondant_profile_c")
	private Double profileC;

	@Column(name = "respondant_profile_d")
	private Double profileD;

	@Column(name = "respondant_ats_id")
	private String atsId;

	@Column(name = "respondant_payroll_id")
	private String payrollId;

	@Column(name = "respondant_redirect_page")
	private String redirectUrl;

	@Column(name = "respondant_email_recipient")
	private String emailRecipient;

	@Column(name = "respondant_score_postmethod")
	private String scorePostMethod;

	// Scoring info
	@Column(name = "respondant_user_agent")
	private String respondantUserAgent;

	@Column(name = "respondant_start_time")
	private Timestamp startTime;

	@Column(name = "respondant_finish_time")
	private Timestamp finishTime;

	@Column(name = "respondant_hire_date")
	private Date hireDate;

}
