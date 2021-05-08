ALTER TABLE employmeo.account_surveys ADD as_confirm_user boolean default true;
ALTER TABLE employmeo.account_surveys ADD as_min_response_length bigint;
ALTER TABLE employmeo.account_surveys ADD as_too_short_text text;
ALTER TABLE employmeo.account_surveys ADD as_too_short_media text;

--//@UNDO

ALTER TABLE employmeo.account_surveys DROP as_confirm_user;
ALTER TABLE employmeo.account_surveys DROP as_min_response_length;
ALTER TABLE employmeo.account_surveys DROP as_too_short_text;
ALTER TABLE employmeo.account_surveys DROP as_too_short_media;
