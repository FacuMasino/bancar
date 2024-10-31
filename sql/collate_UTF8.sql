USE bancar_db;

ALTER DATABASE bancar_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SELECT
    SCHEMA_NAME AS name,
    DEFAULT_CHARACTER_SET_NAME AS character_set,
    DEFAULT_COLLATION_NAME AS collation_name
FROM
    information_schema.SCHEMATA
WHERE
    SCHEMA_NAME = 'bancar_db';
