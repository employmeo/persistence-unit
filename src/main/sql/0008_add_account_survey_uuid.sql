ALTER TABLE employmeo.respondants ALTER COLUMN respondant_partner_id TYPE bigint;
ALTER TABLE employmeo.partners ALTER COLUMN partner_id TYPE bigint;
ALTER TABLE employmeo.account_surveys ADD as_uuid uuid;
ALTER TABLE employmeo.account_surveys ADD as_preamble_media character varying(1083);
ALTER TABLE employmeo.account_surveys ADD as_thankyou_media character varying(1083);
ALTER TABLE employmeo.questions ADD question_media character varying(1083);
ALTER TABLE employmeo.answers ADD answer_media character varying(1083);
ALTER TABLE employmeo.responses ADD response_media character varying(1083);

--//@UNDO

ALTER TABLE employmeo.respondants ALTER COLUMN respondant_partner_id TYPE int;
ALTER TABLE employmeo.partners ALTER COLUMN partner_id TYPE int;
ALTER TABLE employmeo.account_surveys DROP as_uuid;
ALTER TABLE employmeo.account_surveys DROP as_preamble_media;
ALTER TABLE employmeo.account_surveys DROP as_thankyou_media;
ALTER TABLE employmeo.questions DROP question_media;
ALTER TABLE employmeo.answers DROP answer_media;
ALTER TABLE employmeo.responses DROP response_media;