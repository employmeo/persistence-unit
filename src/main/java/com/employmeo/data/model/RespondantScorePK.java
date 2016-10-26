package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondantScorePK implements Serializable {

	@Transient
	private static final long serialVersionUID = 3088515487119303195L;

	@Column(name = "rs_cf_id", insertable = true, updatable = false)
	private Long corefactorId;

	@Column(name = "rs_respondant_id", insertable = true, updatable = false)
	private Long respondantId;
}
