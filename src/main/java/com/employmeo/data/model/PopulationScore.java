package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Entity
@Table(name = "population_scores")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"population","corefactor"})
@ToString(exclude={"population","corefactor"})
public class PopulationScore implements Serializable {

	@Transient
	private static final long serialVersionUID = -7620870646974067861L;


	@EmbeddedId
	@JsonUnwrapped
	private PopulationScorePK id;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ps_population_id", insertable = false, updatable = false)
	private Population population;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ps_corefactor_id", insertable = false, updatable = false)
	private Corefactor corefactor;

	@Column(name = "ps_mean")
	private Double mean;
	
	@Column(name = "ps_significance")
	private Double significance;
	
	@Column(name = "ps_deviation")
	private Double deviation;

	@Column(name = "ps_count")
	private Integer count;

}
