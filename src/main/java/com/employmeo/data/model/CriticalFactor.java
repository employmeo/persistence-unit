package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "critical_factors")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"position","corefactor"})
@ToString(exclude={"position","corefactor"})
public class CriticalFactor implements Serializable {

	@Transient
	private static final long serialVersionUID = -2714745048288530410L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "critical_factor_id")
	private Long id;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "critical_factor_position_id", insertable=false, updatable=false)
	private Position position;

	@Column(name = "critical_factor_position_id")
	private Long positionId;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "critical_factor_corefactor_id", insertable=false, updatable=false)
	private Corefactor corefactor;

	@Column(name = "critical_factor_corefactor_id")
	private Long corefactorId;

	@Column(name = "critical_factor_significance")
	private Double significance;

	@Column(name = "critical_factor_active")
	private Boolean active = Boolean.TRUE;

}
