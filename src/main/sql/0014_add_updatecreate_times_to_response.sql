ALTER TABLE employmeo.responses ADD response_created timestamp with time zone;
ALTER TABLE employmeo.responses ADD response_updated timestamp with time zone;

--//@UNDO

ALTER TABLE employmeo.responses DROP response_updated;
ALTER TABLE employmeo.responses DROP response_created;