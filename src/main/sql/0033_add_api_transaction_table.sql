
CREATE TABLE employmeo.api_transactions
(
  api_key bigserial NOT NULL,
  api_name text,
  api_object_id bigint,
  api_reference_id text,
  api_created_date time with time zone NOT NULL DEFAULT now(),
  CONSTRAINT api_reference_table_pkey PRIMARY KEY (api_key)
)
WITH (
  OIDS=FALSE
);

--//@UNDO

DROP TABLE employmeo.api_transactions;