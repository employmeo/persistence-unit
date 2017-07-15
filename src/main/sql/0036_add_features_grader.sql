ALTER TABLE employmeo.graders ADD grader_useragent text;
ALTER TABLE employmeo.graders ADD grader_ipaddress text;
ALTER TABLE employmeo.graders ADD grader_summary_score text;
ALTER TABLE employmeo.graders ADD grader_relationship text;
ALTER TABLE employmeo.graders ADD grader_account_id bigint;
ALTER TABLE employmeo.criteria ADD is_summary boolean default false;
ALTER TABLE employmeo.criteria ADD is_relationship boolean default false;

--//@UNDO

ALTER TABLE employmeo.graders DROP grader_useragent;
ALTER TABLE employmeo.graders DROP grader_ipaddress;
ALTER TABLE employmeo.graders DROP grader_summary_score;
ALTER TABLE employmeo.graders DROP grader_relationship;
ALTER TABLE employmeo.criteria DROP is_summary;
ALTER TABLE employmeo.criteria DROP is_relationship;