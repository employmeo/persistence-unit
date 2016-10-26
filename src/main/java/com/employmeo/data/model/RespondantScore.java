package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "respondant_scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespondantScore implements Serializable {

	@Transient
	private static final long serialVersionUID = -876140890392862401L;

	@EmbeddedId
	private RespondantScorePK id;

	@ManyToOne
	@JoinColumn(name = "rs_respondant_id", insertable = false, updatable = false)
	private Respondant respondant;

	@Column(name = "rs_question_count")
	private Integer questionCount;

	@Column(name = "rs_value")
	private Double value;

}
