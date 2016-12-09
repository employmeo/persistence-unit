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

--//@UNDO

DROP TABLE employmeo.respondant_nvps;
