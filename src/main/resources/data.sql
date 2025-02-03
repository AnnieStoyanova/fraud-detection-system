CREATE TABLE IF NOT EXISTS blacklisted_countries (
    id SERIAL PRIMARY KEY,
    country_name VARCHAR(100) UNIQUE NOT NULL
);

INSERT INTO blacklisted_countries (country_name) VALUES
('North Korea'),
('Iran'),
('Syria'),
('Sudan'),
('Cuba')
ON CONFLICT (country_name) DO NOTHING;  -- Prevent duplicate entries
