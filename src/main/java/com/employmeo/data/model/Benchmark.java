package com.employmeo.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "benchmarks")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"position","accountSurveys"})
@ToString(exclude={"position","accountSurveys"})
@Slf4j
public class Benchmark implements Serializable {

	@Transient
	private static final long serialVersionUID = -8197029455671270468L;

	public static final int TYPE_SIMPLE = 100;
	public static final int TYPE_PERFORMANCE = 200;
	public static final int TYPE_DETAILED = 300;

	public static final int STATUS_NEW = 100;
	public static final int STATUS_UNSENT = 200;
	public static final int STATUS_SENT = 300;
	public static final int STATUS_COMPLETED = 500;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "benchmark_id")
	private Long id;

	@Column(name = "benchmark_account_id")
	private Long accountId;
		
	@ManyToOne
	@JoinColumn(name = "benchmark_position_id", insertable=false, updatable=false)
	private Position position;

	@Column(name = "benchmark_position_id")
	private Long positionId;

	@Column(name = "benchmark_survey_id")
	private Long surveyId;

	@Column(name = "benchmark_status")
	private Integer status = STATUS_NEW;

	@Column(name = "benchmark_type")
	private Integer type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "benchmark_created_date")
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "benchmark_completed_date")
	private Date completedDate;
	
	@Column(name = "benchmark_invited")
	private Integer invited;

	@Column(name = "benchmark_participant_count")
	private Integer participantCount;
	
	// bi-directional many-to-one association to AccountSurveys
	@JsonManagedReference(value="as-benchmark")
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "benchmark", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<AccountSurvey> accountSurveys = new HashSet<>();
	
	// bi-directional many-to-one association to AccountSurveys
	@JsonManagedReference(value="pop-benchmark")
	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(mappedBy = "benchmark", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<Population> populations = new HashSet<>();

	@PrePersist
	void generateCreatedDate() {
		if(null == createdDate) {
			createdDate = new Date();
			log.debug("Generating create date as {}", createdDate);
		}
	}
}
