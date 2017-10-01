ALTER TABLE employmeo.account_surveys ADD as_min_graders int;
ALTER TABLE employmeo.respondants ADD respondant_ip_address text;
ALTER TABLE employmeo.respondants ADD respondant_wave_grader_min boolean;

--//@UNDO

ALTER TABLE employmeo.account_surveys DROP as_min_graders;
ALTER TABLE employmeo.respondants DROP respondant_ip_address;
ALTER TABLE employmeo.respondants DROP respondant_wave_grader_min;