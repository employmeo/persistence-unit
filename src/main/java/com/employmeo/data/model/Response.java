package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Table(name = "responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response implements Serializable {

	@Transient
	private static final long serialVersionUID = 808486275173441348L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_id")
	private Long id;

	// bi-directional many-to-one association to Respondant
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "response_respondant_id", insertable = false, updatable = false)
	private Respondant respondant;

	@Column(name = "response_respondant_id", insertable = true, updatable = false)
	private Long respondantId;

	@Column(name = "response_text")
	private String responseText;

	@Column(name = "response_value")
	private Integer responseValue;

	// bi-directional many-to-one association to Question
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "response_question_id", insertable = false, updatable = false)
	private Question question;

	@Column(name = "response_question_id", insertable = true, updatable = false)
	private Long questionId;

}
