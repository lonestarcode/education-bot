-- Create the quiz database if it doesn't exist
   CREATE DATABASE quiz_db;
   CREATE USER 'reekdaddy'@'localhost' IDENTIFIED BY 'Cardsbevo89!';
   GRANT ALL PRIVILEGES ON quiz_db.* TO 'reekdaddy'@'localhost';
   FLUSH PRIVILEGES;
   EXIT;

-- Use the newly created database
USE quiz_db;

-- Table for storing quiz questions
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text VARCHAR(255) NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL,  -- Stores the correct answer ('A', 'B', 'C', 'D')
    language VARCHAR(10) DEFAULT 'en' -- To allow for multilingual support
);

-- Insert sample data for questions
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, language)
VALUES
-- U.S. History Questions
('What were the 13 original colonies collectively known as before the American Revolution?', 'The Confederate States', 'The United Colonies', 'The British Colonies', 'The Eastern Federation', 'C', 'en'),
('Who wrote the Declaration of Independence?', 'John Adams', 'Benjamin Franklin', 'Thomas Jefferson', 'James Madison', 'C', 'en'),
('Which event started the American Revolution?', 'The Boston Tea Party', 'The Battle of Bunker Hill', 'The Signing of the Declaration of Independence', 'The Battle of Lexington and Concord', 'D', 'en'),
('Who was the first President of the United States?', 'George Washington', 'John Adams', 'Thomas Jefferson', 'James Madison', 'A', 'en'),
('What year did the American Civil War begin?', '1850', '1861', '1865', '1870', 'B', 'en'),

-- Geography Questions
('What is the capital of France?', 'Paris', 'Lyon', 'Marseille', 'Nice', 'A', 'en'),
('What is the tallest mountain in the world?', 'Mount Kilimanjaro', 'Mount Fuji', 'Mount Everest', 'K2', 'C', 'en'),
('What is the largest ocean on Earth?', 'Atlantic Ocean', 'Pacific Ocean', 'Indian Ocean', 'Arctic Ocean', 'B', 'en'),
('What is the capital of Australia?', 'Sydney', 'Melbourne', 'Canberra', 'Brisbane', 'C', 'en'),
('What is the smallest country in the world by land area?', 'Monaco', 'Vatican City', 'Liechtenstein', 'San Marino', 'B', 'en'),

-- Science Questions
('What is the largest planet in our solar system?', 'Mars', 'Saturn', 'Earth', 'Jupiter', 'D', 'en'),
('What is the chemical symbol for water?', 'CO2', 'H2O', 'O2', 'CH4', 'B', 'en'),
('What is the atomic number of Carbon?', '6', '8', '12', '14', 'A', 'en'),
('What type of bond holds water molecules together?', 'Ionic', 'Covalent', 'Hydrogen', 'Metallic', 'C', 'en'),
('What is the powerhouse of the cell?', 'Nucleus', 'Ribosome', 'Mitochondria', 'Cytoplasm', 'C', 'en'),

-- Economics/Currency Questions
('What is the currency of Brazil?', 'Peso', 'Real', 'Dollar', 'Euro', 'B', 'en'),
('What is the currency of Japan?', 'Yuan', 'Won', 'Yen', 'Ringgit', 'C', 'en'),
('What is the currency of India?', 'Dollar', 'Peso', 'Rupee', 'Dinar', 'C', 'en'),
('Which country uses the Euro?', 'United Kingdom', 'Germany', 'Russia', 'China', 'B', 'en'),
('What is the currency of Russia?', 'Ruble', 'Krona', 'Zloty', 'Hryvnia', 'A', 'en');