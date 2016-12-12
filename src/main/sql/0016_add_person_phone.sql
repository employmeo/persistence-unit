ALTER TABLE employmeo.persons ADD person_phone character varying (40);

--//@UNDO

ALTER TABLE employmeo.persons DROP person_phone;
