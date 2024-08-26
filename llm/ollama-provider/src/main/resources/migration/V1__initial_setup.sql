CREATE TABLE
  IF NOT EXISTS ai_conversations (
    id TEXT PRIMARY KEY,
    a_name TEXT NOT NULL CHECK (a_name <> ''),
    a_description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  );
