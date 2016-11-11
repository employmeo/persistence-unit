ALTER TABLE employmeo.respondants ALTER respondant_uuid DROP DEFAULT;

--//@UNDO

ALTER TABLE employmeo.respondants ALTER respondant_uuid SET DEFAULT gen_random_uuid();
