package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location implements Serializable {

	@Transient
	private static final long serialVersionUID = -6765625000801160708L;

	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "location_name")
	private String locationName;

	// bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name = "location_account_id", insertable = false, updatable = false)
	private Account account;

	@Column(name = "location_account_id", insertable = true, updatable = false)
	private Long accountId;

	@Column(name = "location_city")
	private String city;

	@Column(name = "location_fein")
	private String fein;

	@Column(name = "location_lat")
	private double latitude;

	@Column(name = "location_long")
	private double longitude;

	@Column(name = "location_state")
	private String state;

	@Column(name = "location_street1")
	private String street1;

	@Column(name = "location_street2")
	private String street2;

	@Column(name = "location_zip")
	private String zip;

	@Column(name = "location_ats_id")
	private String atsId;

	@Column(name = "location_payroll_id")
	private String payrollId;


}
