-- 프로필 테이블
ALTER TABLE profile
ADD COLUMN user_id VARCHAR(100) UNIQUE NOT NULL;