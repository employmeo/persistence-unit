package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

/**
 * The persistent class for the corefactor_descriptions database table.
 *
 */
@Entity
@Table(name="corefactor_descriptions")
@Data
@EqualsAndHashCode(exclude={"corefactor"})
@ToString(exclude={"corefactor"})
@NoArgsConstructor
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

	@JsonBackReference(value="cf-desc")
	@ManyToOne
	@JoinColumn(name = "cf_id",insertable=false,updatable=false)
	private Corefactor corefactor;

	@Column(name="cf_id")
	private Long corefactorId;

	@Column(name="cf_low_end")
	private Double lowEnd;
	
	@Column(name="cfdesc_quartile")
	private Integer quartile;

}