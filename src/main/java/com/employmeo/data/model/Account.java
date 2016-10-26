package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<AccountSurvey> accountSurveys;

	// bi-directional many-to-one association to Position
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Position> positions;

	// bi-directional many-to-one association to Position
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Location> locations;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "account")
	private Set<User> users;

	// bi-directional many-to-one association to Position
	@OneToMany(mappedBy = "account")
	// @OrderBy("respondant.respondantCreatedDate DESC")
	private Set<Respondant> respondants;

	// bi-directional many-to-one association to BillingItem
	@OneToMany(mappedBy = "account")
	private Set<BillingItem> billingItems;

}
