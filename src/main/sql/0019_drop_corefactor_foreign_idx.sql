DROP INDEX employmeo.fk_corefactor_foreign_id;

--//@UNDO

CREATE UNIQUE INDEX fk_corefactor_foreign_id
  ON employmeo.corefactors
  USING btree
  (corefactor_foreign_id COLLATE pg_catalog."default");