ALTER TABLE employmeo.reference_check_configs ADD rcc_email_resp_details boolean;

--//@UNDO

ALTER TABLE employmeo.reference_check_configs DROP rcc_email_resp_details;