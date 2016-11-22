CREATE TABLE employmeo.graders
(
  grader_id bigserial NOT NULL,
  grader_type integer,
  grader_status integer,
  grader_response_id bigint,
  grader_question_id bigint,
  grader_person_id bigint,
  grader_user_id bigint,
  grader_respondant_id bigint,
  grader_uuid uuid,
  CONSTRAINT graders_pkey PRIMARY KEY (grader_id),
  CONSTRAINT graders_grader_person_id_fkey FOREIGN KEY (grader_person_id)
      REFERENCES employmeo.persons (person_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT graders_grader_respondant_id_fkey FOREIGN KEY (grader_respondant_id)
      REFERENCES employmeo.respondants (respondant_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT graders_grader_response_id_fkey FOREIGN KEY (grader_response_id)
      REFERENCES employmeo.responses (response_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT graders_grader_question_id_fkey FOREIGN KEY (grader_question_id)
      REFERENCES employmeo.questions (question_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT graders_grader_user_id_fkey FOREIGN KEY (grader_user_id)
      REFERENCES employmeo.users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE employmeo.grades
(
  grade_id bigserial NOT NULL,
  grade_grader_id bigint NOT NULL,
  grade_question_id bigint NOT NULL,
  grade_value int,
  grade_text character varying(1083),
  CONSTRAINT grades_pkey PRIMARY KEY (grade_id),
  CONSTRAINT grades_grader_id_fkey FOREIGN KEY (grade_grader_id)
      REFERENCES employmeo.graders (grader_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT grades_question_id_fkey FOREIGN KEY (grade_question_id)
      REFERENCES employmeo.questions (question_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE employmeo.criteria
(
  criterion_id bigint NOT NULL,
  criterion_question_id bigint NOT NULL,
  criterion_linked_question_id bigint NOT NULL,
  criterion_seq int NOT NULL,
  criterion_required boolean,
  CONSTRAINT criteria_pkey PRIMARY KEY (criterion_id),
  CONSTRAINT criteria_question_id_fkey FOREIGN KEY (criterion_question_id)
      REFERENCES employmeo.questions (question_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT criteria_linked_question_id_fkey FOREIGN KEY (criterion_linked_question_id)    
      REFERENCES employmeo.questions (question_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--//@UNDO

DROP TABLE employmeo.criteria;
DROP TABLE employmeo.grades;
DROP TABLE employmeo.graders;
