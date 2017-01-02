CREATE TABLE employmeo.populations
(
  population_id bigserial,
  population_name text,
  population_target_id bigint,
  population_target_value boolean,
  population_benchmark_id bigint,
  population_size integer,
  population_profile text,
  population_created_date timestamp with time zone,
  CONSTRAINT population_pkey PRIMARY KEY (population_id),
  CONSTRAINT population_population_benchmark_id_fkey FOREIGN KEY (population_benchmark_id)
      REFERENCES employmeo.benchmarks (benchmark_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT population_population_target_id_fkey FOREIGN KEY (population_target_id)
      REFERENCES employmeo.prediction_targets (prediction_target_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE employmeo.population_scores
(
  ps_population_id bigint NOT NULL,
  ps_corefactor_id bigint NOT NULL,
  ps_mean double precision,
  ps_deviation double precision,
  ps_significance double precision,
  ps_count integer,
  CONSTRAINT population_scores_pkey PRIMARY KEY (ps_population_id, ps_corefactor_id),
  CONSTRAINT population_scores_ps_corefactor_id_fkey FOREIGN KEY (ps_corefactor_id)
      REFERENCES employmeo.corefactors (corefactor_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT population_scores_ps_population_id_fkey FOREIGN KEY (ps_population_id)
      REFERENCES employmeo.populations (population_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE employmeo.critical_factors
(
  critical_factor_id bigserial NOT NULL,
  critical_factor_position_id bigint,
  critical_factor_corefactor_id bigint,
  critical_factor_active boolean,
  critical_factor_significance double precision,
  CONSTRAINT critical_factors_pkey PRIMARY KEY (critical_factor_id),
  CONSTRAINT critical_factors_critical_factor_corefactor_id_fkey FOREIGN KEY (critical_factor_corefactor_id)
      REFERENCES employmeo.corefactors (corefactor_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT critical_factors_critical_factor_position_id_fkey FOREIGN KEY (critical_factor_position_id)
      REFERENCES employmeo.positions (position_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--//@UNDO

DROP TABLE employmeo.critical_factors;

DROP TABLE employmeo.population_scores;

DROP TABLE employmeo.populations;

