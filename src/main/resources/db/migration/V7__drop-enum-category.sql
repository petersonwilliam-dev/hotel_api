ALTER TABLE room_type
ALTER COLUMN category TYPE varchar(100) USING category::TEXT;

DROP TYPE room_category_enum;