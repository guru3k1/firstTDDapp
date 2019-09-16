CREATE TABLE IF NOT EXISTS persons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL
);

insert into persons (firstName,lastName) values
    ('John','Connor'),
    ('Sarah','Connor');