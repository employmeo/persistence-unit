ALTER TABLE employmeo.partners ADD partner_client_id text;
ALTER TABLE employmeo.partners ADD partner_oauth text;
ALTER TABLE employmeo.partners ADD partner_client_secret text;

--//@UNDO

ALTER TABLE employmeo.partners DROP partner_client_id text;
ALTER TABLE employmeo.partners DROP partner_oauth text;
ALTER TABLE employmeo.partners DROP partner_client_secret text;