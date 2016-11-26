package com.employmeo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "grades")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"grader","question"})
@ToString(exclude={"grader","question"})
public class Grade implements Serializable {
	
	@Transient  
	private static final long serialVersionUID = -2296717358501604610L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "grade_id")
		private Long id;

		// bi-directional many-to-one association to Grader
		@JsonIgnore
		@ManyToOne
		@JoinColumn(name = "grade_grader_id", insertable = false, updatable = false)
		private Grader grader;

		@Column(name = "grade_grader_id", insertable = true, updatable = false)
		private Long graderId;

		@Column(name = "grade_text")
		private String gradeText;
	
		@Column(name = "grade_value")
		private Integer gradeValue;

		// bi-directional many-to-one association to Question
		@JsonIgnore
		@ManyToOne
		@JoinColumn(name = "grade_question_id", insertable = false, updatable = false)
		private Question question;

		@Column(name = "grade_question_id", insertable = true, updatable = false)
		private Long questionId;
		
		@JsonProperty("questionText")
		public String getQuestionText() {
			if (this.question == null) return null;
			return this.question.getQuestionText();
		}
}