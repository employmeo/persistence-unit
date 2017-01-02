ALTER TABLE employmeo.account_surveys ADD as_permalink text;
ALTER TABLE employmeo.account_surveys ADD as_type integer;
ALTER TABLE employmeo.respondants ADD respondant_type integer;
ALTER TABLE employmeo.surveys ADD survey_availability integer;

CREATE TABLE employmeo.benchmarks
(
  benchmark_id bigserial,
  benchmark_account_id bigint,
  benchmark_survey_id bigint,
  benchmark_position_id bigint,
  benchmark_status integer,
  benchmark_created_date timestamp with time zone,
  benchmark_type integer,
  benchmark_invited integer,
  benchmark_completed_date timestamp with time zone,
  benchmark_participant_count integer,
  CONSTRAINT benchmark_pkey PRIMARY KEY (benchmark_id),
  CONSTRAINT benchmark_benchmakr_position_id_fkey FOREIGN KEY (benchmakr_position_id)
      REFERENCES employmeo.positions (position_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT benchmark_benchmark_account_id_fkey FOREIGN KEY (benchmark_account_id)
      REFERENCES employmeo.accounts (account_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT benchmark_benchmark_survey_id_fkey FOREIGN KEY (benchmark_survey_id)
      REFERENCES employmeo.surveys (survey_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);  
  
ALTER TABLE employmeo.respondants ADD respondant_benchmark_id bigint;
ALTER TABLE employmeo.account_surveys ADD as_benchmark_id bigint;

--//@UNDO

ALTER TABLE employmeo.account_surveys DROP as_benchmark_id;
ALTER TABLE employmeo.respondants DROP respondant_benchmark_id;

DROP TABLE employmeo.benchmarks;

ALTER TABLE employmeo.respondants DROP respondant_type;
ALTER TABLE employmeo.account_surveys DROP as_type;
ALTER TABLE employmeo.account_surveys DROP as_permalink;

