ALTER TABLE employmeo.payroll_entry ADD payroll_entry_paycode character varying(40);
ALTER TABLE employmeo.payroll_entry ADD payroll_entry_paydate date;
ALTER TABLE employmeo.payroll_entry ADD payroll_entry_category character varying(40);
ALTER TABLE employmeo.payroll_entry DROP payroll_entry_bonus_pay;

--//@UNDO

ALTER TABLE employmeo.payroll_entry DROP payroll_entry_paycode;
ALTER TABLE employmeo.payroll_entry DROP payroll_entry_paydate;
ALTER TABLE employmeo.payroll_entry DROP payroll_entry_category;
ALTER TABLE employmeo.payroll_entry ADD payroll_entry_bonus_pay numeric(10,0) DEFAULT (0)::numeric;
