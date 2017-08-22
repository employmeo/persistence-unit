package com.employmeo.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"account"})
@ToString(exclude={"account"})
public class Position implements Serializable {

	@Transient
	private static final long serialVersionUID = 4126199869944171627L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id")
	private Long id;

	@Column(name = "position_name")
	private String positionName;

	@Column(name = "position_description")
	private String description;

	@Column(name = "position_target_hireratio")
	private BigDecimal positionTargetHireratio;

	@Column(name = "position_target_tenure")
	private BigDecimal targetTenure;

	@Column(name = "position_ats_id")
	private String atsId;
	
	// bi-directional many-to-one association to Account
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "position_account", insertable=false, updatable=false)
	private Account account;

	@Column(name = "position_account")
	private Long accountId;
	
	@Column(name = "position_scoring_scale_id")
	private Long scoringScaleId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "position_scoring_scale_id", insertable=false, updatable=false)
	private ScoringScale scoringScale;
	
	@JsonIgnore
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<PositionPredictionConfiguration> positionPredictionConfigurations = new HashSet<>();
	
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "position", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<CriticalFactor> criticalFactors;
	
	@JsonManagedReference(value="workflow-position")
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "position", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private List<CustomWorkflow> customWorkflows;
		
	public ScoringScale getScoringScale() {
		if (this.scoringScale != null) return this.scoringScale;
		return ProfileDefaults.DEFAULT_SCALE;
	}
}
