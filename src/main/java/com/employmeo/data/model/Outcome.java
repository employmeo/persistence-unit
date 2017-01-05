package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.*;

@Entity
@Table(name = "outcomes")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"respondant","predictionTarget"})
@ToString(exclude={"respondant","predictionTarget"})
public class Outcome implements Serializable {

	@Transient
	private static final long serialVersionUID = -7780462963353935634L;

	@EmbeddedId
	@JsonUnwrapped
	private OutcomePK id;

	@JsonBackReference(value="out-respondant")
	@ManyToOne
	@JoinColumn(name = "outcome_respondant_id", insertable = false, updatable = false)
	private Respondant respondant;

	@JsonBackReference(value="out-target")
	@ManyToOne
	@JoinColumn(name = "outcome_target_id", insertable = false, updatable = false)
	private PredictionTarget predictionTarget;

	@Column(name = "outcome_value")
	private Boolean value;

}
