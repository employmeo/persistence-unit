package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the corefactor_descriptions database table.
 * 
 */
@Entity
@Table(name="corefactor_descriptions")
@NamedQuery(name="CorefactorDescription.findAll", query="SELECT c FROM CorefactorDescription c")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorefactorDescription implements Serializable {
	@Transient
	private static final long serialVersionUID = -2439383939589791820L;

	@Id
	@Basic(optional=false)
	@Column(name="cfdesc_id")
	private Long id;

	@Column(name="cf_description")
	private String cfDescription;

	@Column(name="cf_high_end")
	private double cfHighEnd;

	@ManyToOne
	@JoinColumn(name = "cf_id",insertable=false,updatable=false)
	private Corefactor corefactor;
	
	@Column(name="cf_id")
	private Long cfId;

	@Column(name="cf_low_end")
	private double cfLowEnd;

}