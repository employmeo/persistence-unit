package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "predictions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"respondant","positionPredictionConfig"})
@ToString(exclude={"respondant","positionPredictionConfig"})
public class Prediction implements Serializable {

	@Transient
	private static final long serialVersionUID = 3976444277100545830L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prediction_id")
	private Long predictionId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "respondant_id", insertable=false, updatable=false)
	private Respondant respondant;

	@Column(name = "respondant_id")
	private Long respondantId;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "position_prediction_config_id", insertable=false, updatable=false)
	private PositionPredictionConfiguration positionPredictionConfig;

	@Column(name = "position_prediction_config_id")
	private Long positionPredictionConfigId;

	@Column(name = "prediction_foreign_id")
	private String foreignId;

	@Column(name = "prediction_value")
	private Boolean value;
	
	@Column(name = "prediction_target_id")
	private Long targetId;

	
	@Column(name = "prediction_score")
	private Double predictionScore;

	@Column(name = "score_percentile")
	private Double scorePercentile;


	@Column(name = "active")
	private Boolean active = Boolean.TRUE;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

}
