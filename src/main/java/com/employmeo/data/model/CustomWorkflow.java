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
@Table(name = "custom_workflows")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"position"})
@ToString(exclude={"position"})
public class CustomWorkflow implements Serializable {

	@Transient
	private static final long serialVersionUID = 93934417467941938L;

	public static final int TRIGGER_POINT_CREATION = 0;
	public static final int TRIGGER_POINT_ASSESSMENT = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cw_id")
	private Long id;

	@JsonBackReference(value="workflow-position")
	@ManyToOne
	@JoinColumn(name = "cw_position_id", insertable=false, updatable=false)
	private Position position;

	@Column(name = "cs_position_id")
	private Long positionId;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "cw_trigger_point", insertable=false, updatable=false)
	private Integer triggerPoint;

	@Column(name = "cw_type")
	private String type;

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

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "positionPredictionConfig", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Prediction> predictions = new HashSet<>();
	
}
