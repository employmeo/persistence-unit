package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.employmeo.data.model.identifier.CorefactorDescriptionId;

import lombok.Data;
import lombok.NonNull;

/**
 * The persistent class for the corefactor_descriptions database table.
 * 
 */
@Entity
@Table(name="corefactor_descriptions")
@NamedQuery(name="CorefactorDescription.findAll", query="SELECT c FROM CorefactorDescription c")
@Data
public class CorefactorDescription implements Serializable {
	@Transient
	private static final long serialVersionUID = -2439383939589791820L;

	@Id
	@Basic(optional=false)
	@Column(name="cfdesc_id")
	private Long idValue;

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

	public CorefactorDescriptionId getId() {
		return CorefactorDescriptionId.of(this.idValue);
	}
	
	public void setId(@NonNull CorefactorDescriptionId id) {
		this.idValue = id.getLongValue();
	}	
}