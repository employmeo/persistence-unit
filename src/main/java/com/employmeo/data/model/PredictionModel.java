package com.employmeo.data.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "prediction_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionModel implements Serializable {

	@Transient
	private static final long serialVersionUID = 874665185417557763L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prediction_model_id")
	private Integer modelId;

	@Column(name = "model_name")
	private String name;

	@Column(name = "model_type")
	private String modelTypeValue;

	@Column(name = "version")
	private Integer version;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "prediction_target_id")
	private PredictionTarget predictionTarget;

	@OneToMany(mappedBy = "predictionModel", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<PositionPredictionConfiguration> positionPredictionConfigs;

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
