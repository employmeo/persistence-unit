package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutcomePK implements Serializable {
	
	@Transient
	private static final long serialVersionUID = 1L;

	@Column(name = "outcome_respondant_id", insertable = true, updatable = false)
	private Long respondantId;

	@Column(name = "outcome_target_id", insertable = true, updatable = false)
	private Long predictionTargetId;
}
