package com.employmeo.data.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "prediction_models")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"predictionTarget","positionPredictionConfigs"})
@ToString(exclude={"predictionTarget","positionPredictionConfigs"})
public class PredictionModel implements Serializable {

	@Transient
	private static final long serialVersionUID = 874665185417557763L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prediction_model_id")
	private Long modelId;

	@Column(name = "model_name")
	private String name;

	@Column(name = "model_type")
	private String modelTypeValue;

	@Column(name = "version")
	private Integer version;

	@Column(name = "description")
	private String description;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "prediction_target_id", insertable=false, updatable=false)
	private PredictionTarget predictionTarget;

	@Column(name = "prediction_target_id")
	private Long predictionTargetId;

	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "predictionModel", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<PositionPredictionConfiguration> positionPredictionConfigs = new HashSet<>();

	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	public PredictionModelType getModelType() {
		return PredictionModelType.getByValue(this.modelTypeValue);
	}

	public void setModelType(PredictionModelType modelType) {
		this.modelTypeValue = modelType.getValue();
	}

}
