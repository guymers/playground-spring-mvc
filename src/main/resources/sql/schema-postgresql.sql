CREATE SCHEMA testing;

CREATE TABLE testing.widget (
	widget_id serial NOT NULL PRIMARY KEY,
	name VARCHAR(50),
	create_date DATE,
	active BOOLEAN
);
