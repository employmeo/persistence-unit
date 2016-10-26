package com.employmeo.data.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "corefactors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"corefactorDescriptions"})
public class Corefactor implements Serializable {
	@Transient
	private static final long serialVersionUID = -2852093083745544195L;

	@Id
	@Basic(optional=false)
	@Column(name = "corefactor_id")
	private Long id;

	@Column(name = "cf_high")
	private Double highValue;

	@Column(name = "cf_high_description")
	private String highDescription;

	@Column(name = "cf_low")
	private Double lowValue;

	@Column(name = "cf_low_description")
	private String lowDescription;

	@Column(name = "cf_mean_score")
	private Double meanScore;

	@Column(name = "cf_measurements")
	private Long measurements;

	@Column(name = "cf_score_deviation")
	private Double scoreDeviation;

	@Column(name = "cf_source")
	private String source;

	@Column(name = "corefactor_description")
	private String description;

	@Column(name = "corefactor_name")
	private String name;

	@Column(name = "corefactor_foreign_id")
	private String foreignId;

	@Column(name = "cf_display_group")
	private String displayGroup;

	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "corefactor", fetch = FetchType.EAGER)
	private Set<CorefactorDescription> corefactorDescriptions = new HashSet<>();

}
