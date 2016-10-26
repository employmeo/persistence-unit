package com.employmeo.data.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"respondants"})
@ToString(exclude={"respondants"})
public class Person implements Serializable {

	@Transient
	private static final long serialVersionUID = -2093627361040138442L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Integer id;

	@Column(name = "person_email")
	private String email;

	@Column(name = "person_fname")
	private String firstName;

	@Column(name = "person_lname")
	private String lastName;

	@Column(name = "person_ssn")
	private String ssn;

	@Column(name = "person_address")
	private String address;

	@Column(name = "person_lat")
	private Double latitude;

	@Column(name = "person_long")
	private Double longitude;

	@Column(name = "person_ats_id")
	private String atsId;

	// bi-directional many-to-one association to Respondant
	@JsonIgnore
	@OneToMany(mappedBy = "person")
	private Set<Respondant> respondants = new HashSet<>();
}
