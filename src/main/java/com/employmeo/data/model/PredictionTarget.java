package com.employmeo.data.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "prediction_targets")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"positionTargets","predictionModels"})
@ToString(exclude={"positionTargets","predictionModels"})
public class PredictionTarget implements Serializable {

	@Transient
	private static final long serialVersionUID = 6688619019721493911L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prediction_target_id")
	private Integer predictionTargetId;

	@Column(name = "name")
	private String name;

	@Column(name = "label")
	private String label;

	@Column(name = "description")
	private String description;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	@JsonIgnore
	@OneToMany(mappedBy = "predictionTarget", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<PositionPredictionConfiguration> positionTargets = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "predictionTarget", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<PredictionModel> predictionModels = new HashSet<>();




}
