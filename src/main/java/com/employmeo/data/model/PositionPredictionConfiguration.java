package com.employmeo.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "position_prediction_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionPredictionConfiguration implements Serializable {

	@Transient
	private static final long serialVersionUID = 93934417467941938L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_prediction_config_id")
	private Integer positionPredictionConfigId;

	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "prediction_target_id")
	private PredictionTarget predictionTarget;

	@ManyToOne
	@JoinColumn(name = "model_id")
	private PredictionModel predictionModel;

	@Column(name = "target_threshold")
	private BigDecimal targetThreshold;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	@OneToMany(mappedBy = "positionPredictionConfig", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Prediction> predictions;
}
