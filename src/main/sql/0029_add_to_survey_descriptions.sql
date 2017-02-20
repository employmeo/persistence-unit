ALTER TABLE employmeo.surveys ADD survey_oneliner text;

--//@UNDO

ALTER TABLE employmeo.surveys DROP survey_oneliner;
