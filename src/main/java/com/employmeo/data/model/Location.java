package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"account"})
@ToString(exclude={"account"})
public class Location implements Serializable {

	public static final int TYPE_NORMAL = 1; // Normal visible in portal location
	public static final int TYPE_VISIBLE_PARENT = 100; // Parent location (has children) but also visible in portal
	public static final int TYPE_HIDDEN_PARENT = 200; // Parent location (has children) not visible in portal
	
	public static final int STATUS_UNVERIFIED = 0;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_NOT_ACTIVE = 99;
	
	@Transient
	private static final long serialVersionUID = -6765625000801160708L;

	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "location_name")
	private String locationName;

	// bi-directional many-to-one association to Account
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "location_account_id", insertable = false, updatable = false)
	private Account account;

	@Column(name = "location_account_id", insertable = true, updatable = false)
	private Long accountId;

	@Column(name = "location_status")
	private Integer status = Location.STATUS_ACTIVE;
	
	@Column(name = "location_type")
	private Integer type = Location.TYPE_NORMAL;

	@Column(name = "location_city")
	private String city;

	@Column(name = "location_fein")
	private String fein;

	@Column(name = "location_lat")
	private Double latitude;

	@Column(name = "location_long")
	private Double longitude;

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
	
	@Column(name = "location_parent_id")
	private Long parentId;

}
