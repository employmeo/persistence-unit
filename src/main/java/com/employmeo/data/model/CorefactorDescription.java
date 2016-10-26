package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorefactorDescription implements Serializable {
	
	@Transient
	private static final long serialVersionUID = -353768828266224466L;

	@Id
	@Basic(optional=false)
	@Column(name="cfdesc_id")
	private Long id;

	@Column(name="cf_description")
	private String description;

	@Column(name="cf_high_end")
	private Double highEnd;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "cf_id",insertable=false,updatable=false)
	private Corefactor corefactor;
	
	@Column(name="cf_id")
	private Long corefactorId;

	@Column(name="cf_low_end")
	private Double lowEnd;

}