ALTER TABLE employmeo.positions ALTER position_name TYPE text;
ALTER TABLE employmeo.positions ALTER position_description TYPE text;
ALTER TABLE employmeo.respondant_nvps ALTER nvp_name TYPE text;
ALTER TABLE employmeo.respondant_nvps ALTER nvp_value TYPE text;

--//@UNDO

ALTER TABLE employmeo.respondant_nvps ALTER nvp_value TYPE character varying(4096);
ALTER TABLE employmeo.respondant_nvps ALTER nvp_name TYPE character varying(1083);
ALTER TABLE employmeo.positions ALTER position_description TYPE character varying(1083);
ALTER TABLE employmeo.positions ALTER position_name TYPE character varying(45);
