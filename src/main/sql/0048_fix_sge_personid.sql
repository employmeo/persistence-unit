ALTER TABLE employmeo.sendgrid_event ALTER COLUMN person_id TYPE bigint USING (person_id::bigint);

--//@UNDO

ALTER TABLE employmeo.sendgrid_event ALTER COLUMN person_id TYPE text USING (person_id::text);