ALTER TABLE employmeo.account_surveys ADD as_phone_number character varying(30);

--//@UNDO

ALTER TABLE employmeo.account_surveys DROP as_phone_number;