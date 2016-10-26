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

	// bi-directional many-to-one association to Survey
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<AccountSurvey> accountSurveys = new HashSet<>();

	// bi-directional many-to-one association to Position
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Position> positions = new HashSet<>();

	// bi-directional many-to-one association to Position
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Location> locations = new HashSet<>();

	// bi-directional many-to-one association to User
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<User> users = new HashSet<>();

	// bi-directional many-to-one association to Position
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Respondant> respondants = new HashSet<>();

	// bi-directional many-to-one association to BillingItem
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<BillingItem> billingItems = new HashSet<>();

}
