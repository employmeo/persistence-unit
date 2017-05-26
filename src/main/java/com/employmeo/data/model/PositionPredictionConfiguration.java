package com.employmeo.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "position_prediction_config")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"position","predictionTarget","predictionModel","predictions"})
@ToString(exclude={"position","predictionTarget","predictionModel","predictions"})
public class PositionPredictionConfiguration implements Serializable {

	@Transient
	private static final long serialVersionUID = 93934417467941938L;

	public static final int TRIGGER_POINT_CREATION = 0;
	public static final int TRIGGER_POINT_ASSESSMENT = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_prediction_config_id")
	private Long positionPredictionConfigId;

	@JsonBackReference(value="cfg-position")
	@ManyToOne
	@JoinColumn(name = "position_id", insertable=false, updatable=false)
	private Position position;

	@Column(name = "position_id")
	private Long positionId;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "prediction_target_id", insertable=false, updatable=false)
	private PredictionTarget predictionTarget;

	@Column(name = "prediction_target_id")
	private Long predictionTargetId;

	@JsonBackReference(value="cfg-model")
	@ManyToOne
	@JoinColumn(name = "model_id", insertable=false, updatable=false)
	private PredictionModel predictionModel;

	@Column(name = "model_id")
	private Long predictionModelId;

	@Column(name = "target_threshold")
	private BigDecimal targetThreshold;
	
	@Column(name = "prediction_mean")
	private Double mean;
	
	@Column(name = "prediction_stdev")
	private Double stDev;

	@Column(name = "prediction_pop_size")
	private Long popSize;
	
	@Column(name = "active")
	private Boolean active;

	@Column(name = "display_priority")
	private Integer displayPriority;
	
	@Column(name = "prediction_trigger_point")
	private Integer triggerPoint;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "positionPredictionConfig", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Prediction> predictions = new HashSet<>();
	
}
