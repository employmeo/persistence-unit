ALTER TABLE employmeo.locations ADD location_type int default 1;
ALTER TABLE employmeo.locations ADD location_status int default 1;
ALTER TABLE employmeo.positions ADD position_status int default 1;

--//@UNDO

ALTER TABLE employmeo.locations DROP location_type;
ALTER TABLE employmeo.locations DROP location_status;
ALTER TABLE employmeo.positions DROP position_status;