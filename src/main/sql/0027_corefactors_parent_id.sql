ALTER TABLE employmeo.corefactors ADD cf_parent_id BIGINT default NULL;

ALTER TABLE employmeo.corefactors 
ADD constraint fk_corefactors_parentid
FOREIGN KEY (cf_parent_id)
      REFERENCES employmeo.corefactors (corefactor_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE employmeo.answers ADD answer_direction INTEGER NOT NULL DEFAULT 1;
      
--//@UNDO

ALTER TABLE employmeo.corefactors DROP answer_direction;
ALTER TABLE employmeo.corefactors DROP CONSTRAINT fk_corefactors_parentid; 
ALTER TABLE employmeo.corefactors DROP cf_parent_id;