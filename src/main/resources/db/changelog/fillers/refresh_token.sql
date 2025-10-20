CREATE TABLE IF NOT EXISTS bing.refresh_token (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  token VARCHAR(512) NOT NULL UNIQUE,
                                                  login VARCHAR(255) NOT NULL,
                                                  expiry_date TIMESTAMP NOT NULL,
                                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                  revoked BOOLEAN NOT NULL DEFAULT FALSE,
                                                  FOREIGN KEY (login) REFERENCES bing.employee(login) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_login ON bing.refresh_token(login);
CREATE INDEX idx_refresh_token_token ON bing.refresh_token(token);
CREATE INDEX idx_refresh_token_expiry ON bing.refresh_token(expiry_date);