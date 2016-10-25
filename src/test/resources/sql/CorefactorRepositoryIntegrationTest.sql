-- inserting corefactors for integration test.

INSERT INTO employmeo.corefactors(
	corefactor_id, corefactor_name, corefactor_description, cf_low, cf_high, cf_low_description, cf_high_description, cf_mean_score, cf_score_deviation, cf_measurements, cf_source, corefactor_foreign_id, cf_display_group)
	VALUES (1001, 'test-humility', 'humility corefactor', 1, 11, null, null, null, null, 0, 'Test Source', 'ForeignId-Test', 'Humility');
	
	
	