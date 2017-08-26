ALTER TABLE employmeo.respondants ADD respondant_second_asid bigint;
ALTER TABLE employmeo.respondants ADD respondant_error_status boolean DEFAULT false;
ALTER TABLE employmeo.accounts ADD account_second_asid bigint;
ALTER TABLE employmeo.partners ADD partner_api_login text;
ALTER TABLE employmeo.partners ADD partner_api_pass text;
ALTER TABLE employmeo.partners ADD partner_api_key text;

--//@UNDO

ALTER TABLE employmeo.respondants DROP respondant_error_status;
ALTER TABLE employmeo.respondants DROP respondant_second_asid;
ALTER TABLE employmeo.accounts DROP account_second_asid;
ALTER TABLE employmeo.partners DROP partner_api_login text;
ALTER TABLE employmeo.partners DROP partner_api_pass text;
ALTER TABLE employmeo.partners DROP partner_api_key text;