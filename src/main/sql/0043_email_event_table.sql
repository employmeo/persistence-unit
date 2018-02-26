CREATE TABLE employmeo.sendgrid_event
(
    sg_event_id text,
	ip text,
	sg_user_id bigint,
	person_id bigint,
	sg_message_id text,
	useragent text,
	event text,
	email text,
    time_stamp bigint,
	asm_group_id text,
	url text,
    smtp_id text,
    CONSTRAINT sendgrid_event_id_pkey PRIMARY KEY (sg_event_id)
)
WITH (
  OIDS=FALSE
);

--//@UNDO

DROP TABLE employmeo.sendgrid_event;