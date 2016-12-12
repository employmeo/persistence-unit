ALTER TABLE employmeo.positions ADD position_ats_id character varying (255);

--//@UNDO

ALTER TABLE employmeo.positions DROP position_ats_id;
