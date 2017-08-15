ALTER TABLE employmeo.respondant_nvps ADD nvp_use_in_model boolean DEFAULT true;
ALTER TABLE employmeo.respondant_nvps ADD nvp_show_in_portal boolean DEFAULT false;
UPDATE employmeo.respondant_nvps set nvp_use_in_model = true, nvp_show_in_portal = false where nvp_use_in_model is null;
ALTER TABLE employmeo.questions ADD question_modified_date timestamp with time zone;
ALTER TABLE employmeo.questions ADD question_created_date timestamp with time zone;

--//@UNDO

ALTER TABLE employmeo.respondant_nvps DROP nvp_show_in_portal;
ALTER TABLE employmeo.respondant_nvps DROP nvp_use_in_model;
ALTER TABLE employmeo.questions DROP question_modified_date;
ALTER TABLE employmeo.questions DROP question_created_date;