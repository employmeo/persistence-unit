package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopulationScorePK implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Column(name = "ps_population_id", insertable = true, updatable = false)
	private Long populationId;
	
	@Column(name = "ps_corefactor_id", insertable = true, updatable = false)
	private Long corefactorId;

}
