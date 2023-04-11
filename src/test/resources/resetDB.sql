delete from schedule_vote;
delete from vote;
ALTER TABLE vote ALTER COLUMN ID RESTART WITH 1;
delete from associate;
ALTER TABLE associate ALTER COLUMN ID RESTART WITH 1;
delete from schedule;
ALTER TABLE schedule ALTER COLUMN ID RESTART WITH 1;