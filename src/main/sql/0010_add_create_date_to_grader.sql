ALTER TABLE employmeo.graders ADD grader_created_date timestamp;

--//@UNDO

ALTER TABLE employmeo.graders DROP grader_created_date;