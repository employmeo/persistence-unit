package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "populations")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"benchmark","target"})
@ToString(exclude={"benchmark","target"})
@Slf4j
public class Population implements Serializable {

	@Transient
	private static final long serialVersionUID = -8780709484509585071L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "population_id")
	private Long id;

	@Column(name = "population_name")
	private String name;
		
	// bi-directional many-to-one association to Prediction Target
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "population_target_id", insertable=false, updatable=false)
	private PredictionTarget target;

	@Column(name = "population_target_id")
	private Long targetId;

	@Column(name = "population_target_value")
	private Boolean targetValue;
	
	// bi-directional many-to-one association to Prediction Target
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "population_benchmark_id", insertable=false, updatable=false)
	private Benchmark benchmark;

	@Column(name = "population_benchmark_id")
	private Long benchmarkId;
	
	@Column(name = "population_size")
	private Integer size;

	@Column(name = "population_profile")
	private String profile;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "population_created_date")
	private Date createdDate;

	// bi-directional many-to-one association to PopulationScores
	@JsonManagedReference
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "population", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<PopulationScore> populationScores = new HashSet<>();

	@PrePersist
	void generateCreatedDate() {
		if(null == createdDate) {
			createdDate = new Date();
			log.debug("Generating create date as {}", createdDate);
		}
	}
}
