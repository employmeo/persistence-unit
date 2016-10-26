package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "predictions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction implements Serializable {

	@Transient
	private static final long serialVersionUID = 3976444277100545830L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prediction_id")
	private Integer predictionId;

	@ManyToOne
	@JoinColumn(name = "respondant_id")
	private Respondant respondant;

	@ManyToOne
	@JoinColumn(name = "position_prediction_config_id")
	private PositionPredictionConfiguration positionPredictionConfig;

	@Column(name = "prediction_score")
	private Double predictionScore;

	@Column(name = "score_percentile")
	private Double scorePercentile;


	@Column(name = "active")
	private Boolean active = Boolean.TRUE;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

}
