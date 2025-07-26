-- 🧑🏻‍💻 Create MySQL user (admin)
CREATE USER 'sadmin'@'%' IDENTIFIED BY '5Z4gf16Y)}i';
GRANT ALL PRIVILEGES ON *.* TO 'sadmin'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

-- 🎯 Create database
CREATE DATABASE IF NOT EXISTS the_haya_quest;
USE the_haya_quest;

-- 🧍 Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT=1000;

-- 🛡️ Admins Table
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 📜 Hadiths Table
CREATE TABLE IF NOT EXISTS hadiths (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT=2000;

-- ❓ Quiz Questions Table
CREATE TABLE IF NOT EXISTS quiz_questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hadith_id INT NOT NULL,
    question TEXT NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    correct_option TINYINT NOT NULL CHECK (correct_option IN (1,2,3)),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hadith_id) REFERENCES hadiths(id) ON DELETE CASCADE
);


-- 🚀 Seed: Admin Login (username: admin, password: admin123)
INSERT INTO admins (username, password)
VALUES ('admin', 'admin'); 

-- 🚀 Seed: Sample Hadith
INSERT INTO hadiths (title, content)
VALUES 
('The Importance of Intention', 'Actions are judged by intentions, and everyone will be rewarded according to what he intended.');

-- 🚀 Seed: 3 Quiz Questions for that Hadith
INSERT INTO quiz_questions (hadith_id, question, option1, option2, option3, correct_option)
VALUES 
(2000, 'What is judged in a person’s actions?', 'Their results', 'Their intentions', 'Their outcomes', 2),
(2000, 'According to the Hadith, what determines reward?', 'Faith', 'Effort', 'Intention', 3),
(2000, 'This Hadith encourages us to focus on?', 'Our goals', 'Our intentions', 'Our deeds only', 2);
