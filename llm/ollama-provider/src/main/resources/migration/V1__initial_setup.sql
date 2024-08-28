CREATE TABLE
  IF NOT EXISTS ai_conversations (
    id TEXT PRIMARY KEY,
    a_name TEXT NOT NULL CHECK (a_name <> ''),
    a_description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  );

CREATE INDEX idx_ai_conversations_created_at ON ai_conversations (created_at);

CREATE TABLE
  IF NOT EXISTS ai_messages (
    id TEXT PRIMARY KEY,
    conversation_id TEXT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_conversation FOREIGN KEY (conversation_id) REFERENCES ai_conversations (id) ON DELETE CASCADE
  );

CREATE INDEX idx_ai_messages_conversation_id ON ai_messages (conversation_id);
