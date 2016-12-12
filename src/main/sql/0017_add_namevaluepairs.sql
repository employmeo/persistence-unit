CREATE TABLE employmeo.respondant_nvps
(
  nvp_id bigserial NOT NULL,
  nvp_respondant_id bigint,
  nvp_name character varying(1083),
  nvp_value character varying(4096),
  CONSTRAINT respondant_nvps_pkey PRIMARY KEY (nvp_id),
  CONSTRAINT respondant_nvps_nvp_respondant_id_fkey FOREIGN KEY (nvp_respondant_id)
      REFERENCES employmeo.respondants (respondant_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE employmeo.outcomes
(
  outcome_respondant_id bigint NOT NULL,
  outcome_target_id bigint NOT NULL,
  outcome_value boolean,
  CONSTRAINT outcomes_pkey PRIMARY KEY (outcome_respondant_id, outcome_target_id),
  CONSTRAINT outcomes_outcome_respondant_id_fkey FOREIGN KEY (outcome_respondant_id)
      REFERENCES employmeo.respondants (respondant_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT outcomes_outcome_target_id_fkey FOREIGN KEY (outcome_target_id)
      REFERENCES employmeo.prediction_targets (prediction_target_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE OR REPLACE FUNCTION employmeo.pivotnvps()
RETURNS text as
$BODY$
DECLARE
dynamic_columns text;
BEGIN
select array_to_string(array_agg(distinct  'np'||substr(regexp_replace(nvp_name, '[^a-zA-Z]', '', 'g'),1,60)||' text'), ', ') into dynamic_columns from employmeo.respondant_nvps;
return 'select * from crosstab ( ''select Distinct nvp_respondant_id , substr(regexp_replace(nvp_name, ''''[^a-zA-Z]'''', '''''''', ''''g''''),1,60) as name, nvp_value from employmeo.respondant_nvps order by nvp_respondant_id, name '',
       ''select Distinct substr(regexp_replace(nvp_name, ''''[^a-zA-Z]'''', '''''''', ''''g''''),1,60) as name from employmeo.respondant_nvps order by name'' )
        as newtable ( nvp_respondant_id bigint, '|| dynamic_columns ||' )';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION employmeo.pivotoutcomes()
RETURNS text as
$BODY$
DECLARE
dynamic_columns text;
BEGIN
select array_to_string(array_agg(distinct  'outcome_'||outcome_target_id ||' boolean'), ', ') into dynamic_columns from employmeo.outcomes;
return 'select * from crosstab ( ''select Distinct outcome_respondant_id , outcome_target_id, outcome_value from employmeo.outcomes order by outcome_respondant_id, outcome_target_id '', ''select Distinct outcome_target_id from employmeo.outcomes order by outcome_target_id'' )
        as newtable ( outcome_respondant_id bigint, '|| dynamic_columns ||' )';
END;
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION employmeo.makenvpview()
RETURNS void as
$BODY$
DECLARE
dyn_nvps text;
dyn_outcomes text;
BEGIN
select employmeo.pivotnvps() into dyn_nvps;
select employmeo.pivotoutcomes() into dyn_outcomes;
DROP VIEW IF EXISTS employmeo.nvpview;
execute 'create view employmeo.nvpview as '||dyn_nvps;
DROP VIEW IF EXISTS employmeo.outcomeview;
execute 'create view employmeo.outcomeview as '||dyn_outcomes;
END;
$BODY$
LANGUAGE plpgsql;

--//@UNDO

DROP FUNCTION employmeo.makenvpview();
DROP FUNCTION employmeo.pivotnvps();
DROP TABLE employmeo.outcomes;
DROP TABLE employmeo.respondant_nvps;
