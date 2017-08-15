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

	@Column(name = "cw_position_id")
	private Long positionId;
	
	@Column(name = "cw_profile")
	private String profile;

	@Column(name = "cw_trigger_point")
	private Integer triggerPoint;

	@Column(name = "cw_type")
	private String type;
	
	@Column(name = "cw_text")
	private String text;
	
	@Column(name = "cw_notes")
	private String notes;
	
	@Column(name = "cw_active")
	private Boolean active;

	@Column(name = "cw_exec_order")
	private Integer execOrder;
	
}
