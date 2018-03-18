CREATE TABLE employmeo.sendgrid_event
(
  sg_event_id text NOT NULL,
  ip text,
  sg_user_id bigint,
  person_id text,
  sg_message_id text,
  useragent text,
  event text,
  email text,
  time_stamp timestamp without time zone,
  asm_group_id text,
  url text,
  smtp_id text,
  CONSTRAINT sendgrid_event_id_pkey PRIMARY KEY (sg_event_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employmeo.sendgrid_event
  OWNER TO postgres;


CREATE INDEX sendgrid_event_email_idx
  ON employmeo.sendgrid_event
  USING btree
  (email COLLATE pg_catalog."default");

CREATE INDEX sendgrid_event_person_id_idx
  ON employmeo.sendgrid_event
  USING btree
  (person_id COLLATE pg_catalog."default");

--//@UNDO

DROP INDEX employmeo.sendgrid_event_email_idx;
DROP INDEX employmeo.sendgrid_event_person_id_idx;
DROP TABLE employmeo.sendgrid_event;