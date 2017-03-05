package com.employmeo.data.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"accountSurveys","positions","locations","users","respondants","billingItems"})
@ToString(exclude={"accountSurveys","positions","locations","users","respondants","billingItems"})
public class Account implements Serializable {

	@Transient
	private static final long serialVersionUID = 8832104767517823206L;

	public static final int STATUS_NEW = 1; // (not set up yet - e.g., no pos / survey, etc)
	public static final int STATUS_BENCHMARKING = 50;
	public static final int STATUS_READY = 100;
	public static final int STATUS_HOLD = 500;
	public static final int STATUS_CLOSED = 1000;
	
	public static final int TYPE_DEMO = 1;
	public static final int TYPE_TRIAL_SMB = 100;
	public static final int TYPE_CIRCLE = 200;
	public static final int TYPE_TRIANGLE = 300;
	public static final int TYPE_SQUARE = 400;
	public static final int TYPE_ENTERPRISE = 500;
	public static final int TYPE_INTEGRATED = 600;
	public static final int TYPE_MASTER = 0;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;

	@Column(name = "account_ats_partner")
	private Long atsPartnerId;

	@Column(name = "account_default_email")
	private String defaultEmail;

	@Column(name = "account_default_redirect")
	private String defaultRedirect;

	@Column(name = "account_feature_scoring")
	private Boolean featureScoring;

	@Column(name = "account_sentby_text")
	private String sentbyText;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "account_status")
	private Integer accountStatus;

	@Column(name = "account_type")
	private Integer accountType;

	@Column(name = "account_ats_id")
	private String atsId;

	@Column(name = "account_default_location_id")
	private Long defaultLocationId;

	@Column(name = "account_default_position_id")
	private Long defaultPositionId;

	@Column(name = "account_default_asid")
	private Long defaultAsId;
	
	@Column(name = "account_scoring_scale_id")
	private Long scoringScaleId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_scoring_scale_id", insertable=false, updatable=false)
	private ScoringScale scoringScale;
	
	@Column(name = "account_custom_profile_id")
	private Long customProfileId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_custom_profile_id", insertable=false, updatable=false)
	private CustomProfile customProfile;
	
	// bi-directional many-to-one association to Survey
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<AccountSurvey> accountSurveys = new HashSet<>();

	// bi-directional many-to-one association to Position
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Position> positions = new HashSet<>();

	// bi-directional many-to-one association to Locations
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Location> locations = new HashSet<>();

	// bi-directional many-to-one association to User
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<User> users = new HashSet<>();

	// bi-directional many-to-one association to Respondants
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Respondant> respondants = new HashSet<>();

	// bi-directional many-to-one association to BillingItem
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<BillingItem> billingItems = new HashSet<>();

	public CustomProfile getCustomProfile() {
		if (this.customProfile != null) return this.customProfile;
		return ProfileDefaults.DEFAULT_PROFILES;
	}
	
	public ScoringScale getScoringScale() {
		if (this.scoringScale != null) return this.scoringScale;
		return ProfileDefaults.DEFAULT_SCALE;
	}
}
