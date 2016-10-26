package com.employmeo.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	// bi-directional many-to-one association to Account
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "position_account", insertable=false, updatable=false)
	private Account account;

	@Column(name = "position_account")
	private Long accountId;


}
