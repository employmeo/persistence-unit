-- Add unique constraint for a respondant to have a unique response for a question.
-- Fail on duplicate response insertion for same question by a respondant.

ALTER TABLE employmeo.responses 
ADD CONSTRAINT uk_responses_respondant_question UNIQUE (response_respondant_id, response_question_id);

--//@UNDO

ALTER TABLE employmeo.responses  DROP CONSTRAINT uk_responses_respondant_question;


