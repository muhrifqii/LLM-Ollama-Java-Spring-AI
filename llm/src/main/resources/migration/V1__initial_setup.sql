CREATE SCHEMA slaking;

CREATE TABLE IF NOT EXISTS slaking.beds (
    tid SERIAL PRIMARY KEY,
    identifier TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL CHECK ( name <> '' ),
    description TEXT,
    category TEXT NOT NULL CHECK ( category <> '' ),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS beds_id_idx ON slaking.beds (identifier);
CREATE INDEX IF NOT EXISTS beds_category_idx ON slaking.beds (category);
