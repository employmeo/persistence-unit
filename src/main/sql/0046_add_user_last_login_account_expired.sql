
ALTER TABLE employmeo.grader_configs ADD grader_reminders boolean;
ALTER TABLE employmeo.grader_configs ADD grader_reminder_1 int;
ALTER TABLE employmeo.grader_configs ADD grader_reminder_2 int;
ALTER TABLE employmeo.grader_configs ADD grader_reminder_3 int;
ALTER TABLE employmeo.grader_configs ADD grader_expire int;
ALTER TABLE employmeo.users ADD user_last_login timestamp;

--//@UNDO

ALTER TABLE employmeo.users DROP user_last_login;
ALTER TABLE employmeo.grader_configs ADD grader_expire;
ALTER TABLE employmeo.grader_configs DROP grader_reminder_3;
ALTER TABLE employmeo.grader_configs DROP grader_reminder_2;
ALTER TABLE employmeo.grader_configs DROP grader_reminder_1;
ALTER TABLE employmeo.grader_configs DROP grader_reminders;